package com.song.example.dagger;

import java.lang.reflect.Field;

/**
 * Created by le on 3/31/17.
 */

public class User {
    String login;
    int id;// 5105572,
    String avatarUrl;// "https;////avatars3.githubusercontent.com/u/5105572?v=3",
    String gravatarId;// "",
    String url;// "https;////api.github.com/users/songtao542",
    String htmlUrl;// "https;////github.com/songtao542",
    String followersUrl;// "https;////api.github.com/users/songtao542/followers",
    String followingUrl;// "https;////api.github.com/users/songtao542/following{/other_user}",
    String gistsUrl;// "https;////api.github.com/users/songtao542/gists{/gist_id}",
    String starredUrl;// "https;////api.github.com/users/songtao542/starred{/owner}{/repo}",
    String subscriptionsUrl;// "https;////api.github.com/users/songtao542/subscriptions",
    String organizationsUrl;// "https;////api.github.com/users/songtao542/orgs",
    String reposUrl;// "https;////api.github.com/users/songtao542/repos",
    String eventsUrl;// "https;////api.github.com/users/songtao542/events{/privacy}",
    String receivedEventsUrl;// "https;////api.github.com/users/songtao542/received_events",
    String type;// "User",
    boolean siteAdmin;// false,
    String name;// null,
    String company;// null,
    String blog;// null,
    String location;// null,
    String email;// null,
    String hireable;// null,
    String bio;// null,
    int publicRepos;// 2,
    int publicGists;// 0,
    int followers;// 4,
    int following;// 3,
    String createdAt;// "2013-07-28T05;//27;//44Z",
    String updatedAt;// "2017-03-04T15;//21;//22Z"

    @Override
    public String toString() {
        Field[] fields = User.class.getDeclaredFields();
        StringBuilder builder = new StringBuilder("{\n");
        try {
            for (Field f : fields) {
                builder.append(f.getName() + ":" + f.get(this) + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        builder.append("}");
        return builder.toString();
    }
}
