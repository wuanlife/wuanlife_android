package com.wuanan.frostmaki.wuanlife_113.MyGroup;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.wuanan.frostmaki.wuanlife_113.AllGroup.GroupListPosts.GroupPostsActivity;
import com.wuanan.frostmaki.wuanlife_113.CreateEditPost.CreatEditPostActivity;
import com.wuanan.frostmaki.wuanlife_113.Home.HomePosts_listview_Adapter;
import com.wuanan.frostmaki.wuanlife_113.LoginRegisterCancel.BaseActivity;
import com.wuanan.frostmaki.wuanlife_113.MainActivity;
import com.wuanan.frostmaki.wuanlife_113.MyGroup.CreatePost.ViewPagerAdapter;
import com.wuanan.frostmaki.wuanlife_113.Posts.PostsDetailActivity;
import com.wuanan.frostmaki.wuanlife_113.R;
import com.wuanan.frostmaki.wuanlife_113.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_113.Utils.ImageLoader;
import com.wuanan.frostmaki.wuanlife_113.Utils.MyApplication;
import com.wuanan.frostmaki.wuanlife_113.Utils.MySwipeRefreshLayout;
import com.wuanan.frostmaki.wuanlife_113.Utils.Postlist;
import com.wuanan.frostmaki.wuanlife_113.Utils.Posts_listview_BaseAdapter;
import com.wuanan.frostmaki.wuanlife_113.Utils.Xcircleindicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frostmaki on 2016/9/27.
 */
public class MygroupFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener{
    private static View view;
    private View headView;
    private View headViewCreate;

    private MySwipeRefreshLayout mRefreshLayout;
    private ImageButton arrow_update;

    private ListView mlistView;
    private ArrayList<Postlist> arraylist;
    private HomePosts_listview_Adapter posts_listview_baseAdapter;
    private static Context mContext;
    private static Activity activity;

    private ImageButton more;
    private ScrollView scrollView;
    private int index=1;
    private int pageCount=1;
    private static Boolean isVisibilty=false;

    private static ViewPager mPager;
    private static Xcircleindicator mXcircleindicator;


    private RelativeLayout myGroup;
    private LinearLayout nomyGroup;
    private String user_id=null;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.e("message.what",msg.what+"");
            switch (msg.what){
                case 0:

                    ArrayList<Postlist> arrayList= (ArrayList<Postlist>) msg.obj;
                    //Log.e("postlist",arrayList.toString());
                    posts_listview_baseAdapter = new HomePosts_listview_Adapter(
                            mContext,
                            arrayList);
                    pageCount= arrayList.get(0).getPageCount();
                    mlistView.setAdapter(posts_listview_baseAdapter);
                    setListViewHeightBasedOnChildren(mlistView);
                    break;
                case 1:
                    myGroup.setVisibility(View.GONE);
                    nomyGroup.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    join_linearLayout.setVisibility(View.GONE);
                    break;
                case 3 :

                    if (joinlist.size()>0){
                        join_button_1.setVisibility(View.VISIBLE);
                        join_button_1.setText(joinlist.get(0).getName());
                        if (joinlist.size()>1){
                            join_button_2.setVisibility(View.VISIBLE);
                            join_button_2.setText(joinlist.get(1).getName());

                        }
                    }
                    break;
                case 4 :
                    create_linearLayout.setVisibility(View.GONE);
                    break;
                case 5 :

                    if (createlist.size()>0){
                        create_button_1.setVisibility(View.VISIBLE);
                        create_button_1.setText(createlist.get(0).getName());
                        if (createlist.size()>1){
                            create_button_2.setVisibility(View.VISIBLE);
                            create_button_2.setText(createlist.get(1).getName());
                        }
                    }
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_mygroup_content,container,false);
        initView();
        if (MyApplication.getisLogin()==true){
            user_id=MyApplication.getUserInfo().get(0).get("userID");
            nomyGroup.setVisibility(View.GONE);
            myGroup.setVisibility(View.VISIBLE);
            getRes();

            initjoinView();
            initjoinData();

            initcreateView();
            initcreateData();
        }else {
            Log.e("false","false         ");
            myGroup.setVisibility(View.GONE);
            nomyGroup.setVisibility(View.VISIBLE);
        }

        return  view;
    }

    private void initView() {
        activity=getActivity();
        mContext=getActivity().getApplicationContext();

        mlistView= (ListView) view.findViewById(R.id.listView_mygroup);
        arraylist= new ArrayList<>();
        //pre = (Button) view.findViewById(R.id.pre);
        //next = (Button) view.findViewById(R.id.next);
        //currentPage = (Button) view.findViewById(R.id.currentPage);

        myGroup= (RelativeLayout) view.findViewById(R.id.myGroup);
        nomyGroup= (LinearLayout) view.findViewById(R.id.nomyGroup);

        //scrollView= (ScrollView) view.findViewById(R.id.scrollView_mygroup);

        headView=LayoutInflater.from(mContext).inflate(R.layout.fragment_mygroup_frame_content,null);
        mlistView.addHeaderView(headView);

        headViewCreate=LayoutInflater.from(mContext).inflate(R.layout.fragment_mygroup_frame_content_create,null);
        mlistView.addHeaderView(headViewCreate);

        more= (ImageButton) view.findViewById(R.id.more);

        arrow_update= (ImageButton) view.findViewById(R.id.arrow_update);
        arrow_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setTabVisibilty();
            }
        });

        mRefreshLayout= (MySwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //mRefreshLayout.setEnabled(false);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                longTimeOperation();
            }
        });
        mRefreshLayout.setOnLoadListener(new MySwipeRefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                loadMore();

            }
        });

        //pre.setOnClickListener(this);
        //next.setOnClickListener(this);
        more.setOnClickListener(this);
        mlistView.setOnItemClickListener(this);
        mlistView.setFocusable(false);
        mlistView.setFocusableInTouchMode(false);
        mlistView.requestFocus();

        relay= (RelativeLayout) view.findViewById(R.id.myGroup_createpost);
    }



    private void loadMore() {
        if (index<pageCount) {
            mRefreshLayout.setRefreshing(true);

            ++index;
            getRes();
            posts_listview_baseAdapter.notifyDataSetChanged();

            mRefreshLayout.setRefreshing(false);
        }else {
            Toast.makeText(mContext,"已经是最后一页",Toast.LENGTH_SHORT).show();
        }
    }

    private void longTimeOperation() {
        if (index > 1) {
            mRefreshLayout.setRefreshing(true);
            --index;
            getRes();
            posts_listview_baseAdapter.notifyDataSetChanged();
            mRefreshLayout.setRefreshing(false);
        } else {
            mRefreshLayout.setRefreshing(true);

            getRes();
            posts_listview_baseAdapter.notifyDataSetChanged();
            mRefreshLayout.setRefreshing(false);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.more :
                Intent intent=new Intent(getActivity().getApplicationContext(), BaseActivity.class);
                intent.putExtra("code",3);
                startActivity(intent);
                break;
            case R.id.more_create :
                Intent intent_create=new Intent(getActivity().getApplicationContext(), BaseActivity.class);
                intent_create.putExtra("code",3);
                startActivity(intent_create);
                break;
            case R.id.group_1 :
                int id=joinlist.get(0).getId();
                String name=joinlist.get(0).getName();

                openThisGroup(id,name);
                break;
            case R.id.group_2 :
                int id_2=joinlist.get(1).getId();
                String name_2=joinlist.get(1).getName();

                openThisGroup(id_2,name_2);
                break;
            case R.id.create_group_1 :
                int id_create=createlist.get(0).getId();
                String name_create=createlist.get(0).getName();

                openThisGroup(id_create,name_create);
                break;
            case R.id.create_group_2 :
                int id_2_create=createlist.get(1).getId();
                String name_2_create=createlist.get(1).getName();

                openThisGroup(id_2_create,name_2_create);
                break;
            default:
                break;
        }
    }

    private void getRes(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (MyApplication.getUserInfo()!=null) {
                    String ApiHost = MyApplication.getApiHost();
                    String Pr_URL = "http://" + ApiHost
                            + "/?service=Post.GetMyGroupPost&id=" + user_id
                            + "&pn=" + index;
                    String JsonData = Http_Url.getUrlReponse(Pr_URL);
                    arraylist = MyGroupJson.getJSONParse(JsonData);

                    if (arraylist!=null) {
                        //wo的星球帖子信息

                        MyApplication.setMyGroupPostsInfo(arraylist);
                        Message message = new Message();
                        message.what = 0;
                        message.obj = arraylist;
                        handler.sendMessage(message);
                    } else {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }

            }
        }).start();


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int data1 = 0;
        if (MyApplication.getMyGroupPostsInfo() != null) {
            data1 = MyApplication.getMyGroupPostsInfo().get(position-2).getPostID();
            Intent intent = new Intent(getActivity().getApplicationContext(), PostsDetailActivity.class);
            intent.putExtra("postID", data1);
            intent.putExtra("groupName", MyApplication.getMyGroupPostsInfo().get(position-2).getGroupName());

            startActivity(intent);
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    /*
    join
     */
    //private View view;
    private LinearLayout join_linearLayout;
    private TextView join_textView;
    private ImageButton join_imageButton;
    private Button join_button_1;
    private Button join_button_2;

    private ArrayList<JoinCreateGroup> joinlist=null;

    private void initjoinView() {
        join_textView= (TextView) headView.findViewById(R.id.name);
        join_imageButton= (ImageButton) headView.findViewById(R.id.more);
        join_button_1= (Button) headView.findViewById(R.id.group_1);
        join_button_2= (Button) headView.findViewById(R.id.group_2);
        join_linearLayout= (LinearLayout) headView.findViewById(R.id.content_fragment);

        join_textView.setText("已加入的星球");
        join_button_1.setVisibility(View.GONE);
        join_button_2.setVisibility(View.GONE);
        join_imageButton.setOnClickListener(this);
        join_button_1.setOnClickListener(this);
        join_button_2.setOnClickListener(this);

        join_button_1.setTextColor(Color.BLACK);
        join_button_2.setTextColor(Color.BLACK);
    }

    private void initjoinData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (MyApplication.getUserInfo() != null) {
                    String ApiHost = MyApplication.getApiHost();
                    String user_id = MyApplication.getUserInfo().get(0).get("userID");
                    String Pr_URL = "http://" + ApiHost
                            + "/?service=Group.GetJoined&user_id=" + user_id;
                    String resultdata = Http_Url.getUrlReponse(Pr_URL);

                    try {
                        JSONObject jsonobject = new JSONObject(resultdata);
                        JSONObject data = jsonobject.getJSONObject("data");
                        JSONArray groups = data.getJSONArray("groups");
                        if (groups.length()>0) {
                            joinlist = new ArrayList<JoinCreateGroup>();
                            for (int i = 0; i < groups.length(); i++) {
                                JoinCreateGroup mlist = new JoinCreateGroup();

                                mlist.setNum(data.getInt("num"));
                                mlist.setPageCount(data.getInt("pageCount"));
                                mlist.setCurrentPage(data.getInt("currentPage"));
                                mlist.setUser_name(data.getString("user_name"));

                                mlist.setName(groups.getJSONObject(i).getString("name"));
                                mlist.setId(groups.getJSONObject(i).getInt("id"));//星球ID
                                mlist.setG_image(groups.getJSONObject(i).getString("g_image"));
                                mlist.setG_introduction(groups.getJSONObject(i).getString("g_introduction"));

                                joinlist.add(mlist);
                            }
                        }
                        MyApplication.setJoinGroupArrayList(joinlist);
                        Message message=new Message();
                        if (joinlist==null){
                            message.what=2;
                        }else {
                            message.what=3;
                            message.obj=arraylist;
                        }
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

        }).start();
    }

    private void openThisGroup(int id,String name) {
        Intent intent=new Intent(getActivity(), GroupPostsActivity.class);
        intent.putExtra("group_id",id);
        intent.putExtra("group_name",name);

        startActivity(intent);
    }



    /*
    create
     */

    private LinearLayout create_linearLayout;
    private TextView create_textView;
    private ImageButton create_imageButton;
    private Button create_button_1;
    private Button create_button_2;

    private ArrayList<JoinCreateGroup> createlist=null;

    private void initcreateView() {
        create_textView= (TextView) headViewCreate.findViewById(R.id.name);
        create_imageButton= (ImageButton) headViewCreate.findViewById(R.id.more_create);
        create_button_1= (Button) headViewCreate.findViewById(R.id.create_group_1);
        create_button_2= (Button) headViewCreate.findViewById(R.id.create_group_2);
        create_linearLayout= (LinearLayout) headViewCreate.findViewById(R.id.content_fragment);

        create_textView.setText("已创建的星球");
        create_button_1.setVisibility(View.GONE);
        create_button_2.setVisibility(View.GONE);
        create_imageButton.setOnClickListener(this);
        create_button_1.setOnClickListener(this);
        create_button_2.setOnClickListener(this);

        create_button_1.setTextColor(Color.BLACK);
        create_button_2.setTextColor(Color.BLACK);
    }

    private void initcreateData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (MyApplication.getUserInfo() != null) {
                    String ApiHost = MyApplication.getApiHost();
                    String user_id = MyApplication.getUserInfo().get(0).get("userID");
                    String Pr_URL = "http://" + ApiHost
                            + "/?service=Group.GetCreate&user_id=" + user_id;
                    String resultdata = Http_Url.getUrlReponse(Pr_URL);

                    try {
                        JSONObject jsonobject = new JSONObject(resultdata);
                        JSONObject data = jsonobject.getJSONObject("data");
                        JSONArray groups = data.getJSONArray("groups");
                        if (groups.length()>0) {
                            createlist = new ArrayList<JoinCreateGroup>();
                            for (int i = 0; i < groups.length(); i++) {
                                JoinCreateGroup mlist = new JoinCreateGroup();

                                mlist.setNum(data.getInt("num"));
                                mlist.setPageCount(data.getInt("pageCount"));
                                mlist.setCurrentPage(data.getInt("currentPage"));
                                mlist.setUser_name(data.getString("user_name"));

                                mlist.setName(groups.getJSONObject(i).getString("name"));
                                mlist.setId(groups.getJSONObject(i).getInt("id"));//星球ID
                                mlist.setG_image(groups.getJSONObject(i).getString("g_image"));
                                mlist.setG_introduction(groups.getJSONObject(i).getString("g_introduction"));
                                createlist.add(mlist);
                            }
                        }
                        MyApplication.setCreateGroupArrayList(createlist);
                        Message message=new Message();

                        if (createlist==null){
                            message.what=4;
                        }else {
                            message.what=5;
                            message.obj=createlist;
                        }
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

        }).start();
    }


    private static RelativeLayout relay;

public static void createPost(){
    if (relay.getVisibility()==View.GONE){
        if (MyApplication.getMyGroupPostsInfo()==null&&
                MyApplication.getJoinGroupArrayList()==null&&
                MyApplication.getCreateGroupArrayList()==null){
            Toast.makeText(mContext,"您的星球什么都没有，先干些其他事吧",Toast.LENGTH_SHORT).show();
        }else {
            createpostMethod();
            isVisibilty = true;
        }
    }else {
        relay.setVisibility(View.GONE);
        isVisibilty=false;
    }
}


    public static void createpostMethod(){

        relay.setVisibility(View.VISIBLE);
        final List<View> viewpagelist=new ArrayList<View>();
        mXcircleindicator=(Xcircleindicator)view. findViewById(R.id.Xcircleindicator);
        mPager=(ViewPager) view.findViewById(R.id.ViewPager);

        View selectview;
        GridView selectGrid=null;
        final List<JoinCreateGroup> datalist=MyApplication.getJoinGroupArrayList();
        gridviewAdapter adapter;
        List<JoinCreateGroup> everlist=null;
        for (int i=1;i<=datalist.size();i++){
            int j=1;
            if ((i % 6)==0){
                everlist=new ArrayList<JoinCreateGroup>();
                for (;j<=i;j++){

                    everlist.add(datalist.get(j-1));
                    //Log.e("everlist",everlist+"");
                }
                selectview=LayoutInflater.from(mContext).inflate(R.layout.fragment_mygroup_selectgroup, null);
                selectGrid= (GridView) selectview.findViewById(R.id.selectGridView);
                adapter=new gridviewAdapter(everlist);
                selectGrid.setAdapter(adapter);
                viewpagelist.add(selectview);
                j=i+1;
            }
        }
        if(datalist.size()<6&&datalist.size()>0){
            everlist=new ArrayList<JoinCreateGroup>();
            for (int j=1;j<=datalist.size();j++){

                everlist.add(datalist.get(j-1));
            }
            selectview=LayoutInflater.from(mContext).inflate(R.layout.fragment_mygroup_selectgroup, null);
            selectGrid= (GridView) selectview.findViewById(R.id.selectGridView);
            adapter=new gridviewAdapter(everlist);
            selectGrid.setAdapter(adapter);
            viewpagelist.add(selectview);
        }
        ViewPagerAdapter mAdapter=new ViewPagerAdapter(viewpagelist);
        mPager.setAdapter(mAdapter);

        mXcircleindicator.initData(viewpagelist.size(), 0);

        mXcircleindicator.setCurrentPage(0);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mXcircleindicator.setCurrentPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    selectGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int group_id=datalist.get((mPager.getCurrentItem())*6+position).getId();
        String group_name=datalist.get((mPager.getCurrentItem())*6+position).getName();
        String userID=MyApplication.getUserInfo().get(0).get("userID");

        Intent intent=new Intent(mContext, CreatEditPostActivity.class);
        intent.putExtra("code",2);
        intent.putExtra("groupName",group_name);
        intent.putExtra("group_id",group_id);
        intent.putExtra("user_id",userID);
        activity.startActivity(intent);

    }
});
    }

    private static class gridviewAdapter extends BaseAdapter{
        private List<JoinCreateGroup> lists;
        public gridviewAdapter(List<JoinCreateGroup> s){
            lists=s;
        }
        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView==null){
                viewHolder=new ViewHolder();
                convertView=LayoutInflater.from(mContext).inflate(R.layout.viewpage_indictor_item,parent,false);
                viewHolder.imageView= (ImageView) convertView.findViewById(R.id.image);//lists.get(position).getG_image();
                viewHolder.textView= (TextView) convertView.findViewById(R.id.text);//lists.get(position).getName()
                convertView.setTag(viewHolder);

            }else {
                viewHolder= (ViewHolder) convertView.getTag();
            }
            String urlTag=lists.get(position).getG_image();
            if (urlTag!=null) {
                viewHolder.imageView.setTag(urlTag);
                new ImageLoader().showImageByThread(viewHolder.imageView, urlTag);
            }
            viewHolder.textView.setTextColor(Color.BLACK);
            viewHolder.textView.setText(lists.get(position).getName());
            return convertView;
        }

        class ViewHolder{
            ImageView imageView;
            TextView textView;
        }
    }


}
