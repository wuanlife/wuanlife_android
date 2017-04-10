package com.wuanla.www.wuanlife_120.http.Post;

import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */

public class PostGetIndex {


    /**
     * ret : 200
     * data : {"page_count":5,"currentPage":1,"posts":[{"posts":{"post_id":"42","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-16 11:09:13","approved":"0","approved_num":"3","collected":"0","collected_num":"0","replied":"0","replied_num":"0","image":[]},"users":{"profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","user_name":"汪汪汪","user_id":"2"},"groups":{"group_id":"1","g_name":"装备2014中队"}},{"posts":{"post_id":"41","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-13 22:06:15","approved":"0","approved_num":"0","collected":"0","collected_num":"0","replied":"0","replied_num":"0","image":[]},"users":{"profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","user_name":"汪汪汪","user_id":"2"},"groups":{"group_id":"1","g_name":"装备2014中队"}},{"posts":{"post_id":"40","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-13 22:06:11","approved":"0","approved_num":"0","collected":"0","collected_num":"0","replied":"0","replied_num":"0","image":[]},"users":{"profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","user_name":"汪汪汪","user_id":"2"},"groups":{"group_id":"1","g_name":"装备2014中队"}},{"posts":{"post_id":"39","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-13 21:49:11","approved":"0","approved_num":"0","collected":"0","collected_num":"0","replied":"0","replied_num":"0","image":[]},"users":{"profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","user_name":"汪汪汪","user_id":"2"},"groups":{"group_id":"1","g_name":"装备2014中队"}},{"posts":{"post_id":"38","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-13 21:23:39","approved":"0","approved_num":"0","collected":"0","collected_num":"0","replied":"0","replied_num":"0","image":[]},"users":{"profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","user_name":"汪汪汪","user_id":"2"},"groups":{"group_id":"1","g_name":"装备2014中队"}},{"posts":{"post_id":"36","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-03 13:37:51","approved":"0","approved_num":"0","collected":"0","collected_num":"0","replied":"0","replied_num":"0","image":[]},"users":{"profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","user_name":"xjkui","user_id":"127"},"groups":{"group_id":"330","g_name":"前路行丶行上天"}},{"posts":{"post_id":"35","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-03 13:37:46","approved":"0","approved_num":"0","collected":"0","collected_num":"0","replied":"0","replied_num":"0","image":[]},"users":{"profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","user_name":"xjkui","user_id":"127"},"groups":{"group_id":"330","g_name":"前路行丶行上天"}},{"posts":{"post_id":"34","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-03 13:37:41","approved":"0","approved_num":"0","collected":"0","collected_num":"0","replied":"0","replied_num":"0","image":[]},"users":{"profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","user_name":"xjkui","user_id":"127"},"groups":{"group_id":"330","g_name":"前路行丶行上天"}},{"posts":{"post_id":"33","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-03 13:37:38","approved":"0","approved_num":"0","collected":"0","collected_num":"0","replied":"0","replied_num":"0","image":[]},"users":{"profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","user_name":"xjkui","user_id":"127"},"groups":{"group_id":"330","g_name":"前路行丶行上天"}},{"posts":{"post_id":"32","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-03 13:37:34","approved":"0","approved_num":"0","collected":"0","collected_num":"0","replied":"0","replied_num":"0","image":[]},"users":{"profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","user_name":"xjkui","user_id":"127"},"groups":{"group_id":"330","g_name":"前路行丶行上天"}}]}
     * msg : null
     */

    private int ret;
    private DataBean data;
    private Object msg;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * page_count : 5
         * currentPage : 1
         * posts : [{"posts":{"post_id":"42","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-16 11:09:13","approved":"0","approved_num":"3","collected":"0","collected_num":"0","replied":"0","replied_num":"0","image":[]},"users":{"profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","user_name":"汪汪汪","user_id":"2"},"groups":{"group_id":"1","g_name":"装备2014中队"}},{"posts":{"post_id":"41","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-13 22:06:15","approved":"0","approved_num":"0","collected":"0","collected_num":"0","replied":"0","replied_num":"0","image":[]},"users":{"profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","user_name":"汪汪汪","user_id":"2"},"groups":{"group_id":"1","g_name":"装备2014中队"}},{"posts":{"post_id":"40","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-13 22:06:11","approved":"0","approved_num":"0","collected":"0","collected_num":"0","replied":"0","replied_num":"0","image":[]},"users":{"profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","user_name":"汪汪汪","user_id":"2"},"groups":{"group_id":"1","g_name":"装备2014中队"}},{"posts":{"post_id":"39","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-13 21:49:11","approved":"0","approved_num":"0","collected":"0","collected_num":"0","replied":"0","replied_num":"0","image":[]},"users":{"profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","user_name":"汪汪汪","user_id":"2"},"groups":{"group_id":"1","g_name":"装备2014中队"}},{"posts":{"post_id":"38","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-13 21:23:39","approved":"0","approved_num":"0","collected":"0","collected_num":"0","replied":"0","replied_num":"0","image":[]},"users":{"profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","user_name":"汪汪汪","user_id":"2"},"groups":{"group_id":"1","g_name":"装备2014中队"}},{"posts":{"post_id":"36","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-03 13:37:51","approved":"0","approved_num":"0","collected":"0","collected_num":"0","replied":"0","replied_num":"0","image":[]},"users":{"profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","user_name":"xjkui","user_id":"127"},"groups":{"group_id":"330","g_name":"前路行丶行上天"}},{"posts":{"post_id":"35","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-03 13:37:46","approved":"0","approved_num":"0","collected":"0","collected_num":"0","replied":"0","replied_num":"0","image":[]},"users":{"profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","user_name":"xjkui","user_id":"127"},"groups":{"group_id":"330","g_name":"前路行丶行上天"}},{"posts":{"post_id":"34","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-03 13:37:41","approved":"0","approved_num":"0","collected":"0","collected_num":"0","replied":"0","replied_num":"0","image":[]},"users":{"profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","user_name":"xjkui","user_id":"127"},"groups":{"group_id":"330","g_name":"前路行丶行上天"}},{"posts":{"post_id":"33","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-03 13:37:38","approved":"0","approved_num":"0","collected":"0","collected_num":"0","replied":"0","replied_num":"0","image":[]},"users":{"profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","user_name":"xjkui","user_id":"127"},"groups":{"group_id":"330","g_name":"前路行丶行上天"}},{"posts":{"post_id":"32","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-03 13:37:34","approved":"0","approved_num":"0","collected":"0","collected_num":"0","replied":"0","replied_num":"0","image":[]},"users":{"profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","user_name":"xjkui","user_id":"127"},"groups":{"group_id":"330","g_name":"前路行丶行上天"}}]
         */

        private int page_count;
        private int currentPage;
        private List<PostsBeanX> posts;

        public int getPage_count() {
            return page_count;
        }

        public void setPage_count(int page_count) {
            this.page_count = page_count;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public List<PostsBeanX> getPosts() {
            return posts;
        }

        public void setPosts(List<PostsBeanX> posts) {
            this.posts = posts;
        }

        public static class PostsBeanX {
            /**
             * posts : {"post_id":"42","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-16 11:09:13","approved":"0","approved_num":"3","collected":"0","collected_num":"0","replied":"0","replied_num":"0","image":[]}
             * users : {"profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","user_name":"汪汪汪","user_id":"2"}
             * groups : {"group_id":"1","g_name":"装备2014中队"}
             */

            private PostsBean posts;
            private UsersBean users;
            private GroupsBean groups;

            public PostsBean getPosts() {
                return posts;
            }

            public void setPosts(PostsBean posts) {
                this.posts = posts;
            }

            public UsersBean getUsers() {
                return users;
            }

            public void setUsers(UsersBean users) {
                this.users = users;
            }

            public GroupsBean getGroups() {
                return groups;
            }

            public void setGroups(GroupsBean groups) {
                this.groups = groups;
            }

            public static class PostsBean {
                /**
                 * post_id : 42
                 * p_title : 1
                 * p_text : 1
                 * lock : 0
                 * create_time : 2017-03-16 11:09:13
                 * approved : 0
                 * approved_num : 3
                 * collected : 0
                 * collected_num : 0
                 * replied : 0
                 * replied_num : 0
                 * image : []
                 */

                private String post_id;
                private String p_title;
                private String p_text;
                private String lock;
                private String create_time;
                private String approved;
                private String approved_num;
                private String collected;
                private String collected_num;
                private String replied;
                private String replied_num;
                private List<?> image;

                public String getPost_id() {
                    return post_id;
                }

                public void setPost_id(String post_id) {
                    this.post_id = post_id;
                }

                public String getP_title() {
                    return p_title;
                }

                public void setP_title(String p_title) {
                    this.p_title = p_title;
                }

                public String getP_text() {
                    return p_text;
                }

                public void setP_text(String p_text) {
                    this.p_text = p_text;
                }

                public String getLock() {
                    return lock;
                }

                public void setLock(String lock) {
                    this.lock = lock;
                }

                public String getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(String create_time) {
                    this.create_time = create_time;
                }

                public String getApproved() {
                    return approved;
                }

                public void setApproved(String approved) {
                    this.approved = approved;
                }

                public String getApproved_num() {
                    return approved_num;
                }

                public void setApproved_num(String approved_num) {
                    this.approved_num = approved_num;
                }

                public String getCollected() {
                    return collected;
                }

                public void setCollected(String collected) {
                    this.collected = collected;
                }

                public String getCollected_num() {
                    return collected_num;
                }

                public void setCollected_num(String collected_num) {
                    this.collected_num = collected_num;
                }

                public String getReplied() {
                    return replied;
                }

                public void setReplied(String replied) {
                    this.replied = replied;
                }

                public String getReplied_num() {
                    return replied_num;
                }

                public void setReplied_num(String replied_num) {
                    this.replied_num = replied_num;
                }

                public List<?> getImage() {
                    return image;
                }

                public void setImage(List<?> image) {
                    this.image = image;
                }
            }

            public static class UsersBean {
                /**
                 * profile_picture : http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100
                 * user_name : 汪汪汪
                 * user_id : 2
                 */

                private String profile_picture;
                private String user_name;
                private String user_id;

                public String getProfile_picture() {
                    return profile_picture;
                }

                public void setProfile_picture(String profile_picture) {
                    this.profile_picture = profile_picture;
                }

                public String getUser_name() {
                    return user_name;
                }

                public void setUser_name(String user_name) {
                    this.user_name = user_name;
                }

                public String getUser_id() {
                    return user_id;
                }

                public void setUser_id(String user_id) {
                    this.user_id = user_id;
                }
            }

            public static class GroupsBean {
                /**
                 * group_id : 1
                 * g_name : 装备2014中队
                 */

                private String group_id;
                private String g_name;

                public String getGroup_id() {
                    return group_id;
                }

                public void setGroup_id(String group_id) {
                    this.group_id = group_id;
                }

                public String getG_name() {
                    return g_name;
                }

                public void setG_name(String g_name) {
                    this.g_name = g_name;
                }
            }
        }
    }
}
