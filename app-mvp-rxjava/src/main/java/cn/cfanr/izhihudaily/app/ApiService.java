package cn.cfanr.izhihudaily.app;

import cn.cfanr.izhihudaily.model.CommentList;
import cn.cfanr.izhihudaily.model.Launch;
import cn.cfanr.izhihudaily.model.NewsDetail;
import cn.cfanr.izhihudaily.model.NewsExtra;
import cn.cfanr.izhihudaily.model.NewsList;
import cn.cfanr.izhihudaily.model.ThemeDaily;
import cn.cfanr.izhihudaily.model.ThemeList;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author xifan
 *         date: 2016/8/16
 *         desc:
 */
public interface ApiService {

    @GET("start-image/{size}")
    Observable<Launch> loadLaunch(@Path("size") String size);

    @GET("news/{type}")
    Observable<NewsList> loadHomeNews(@Path("type") String type);

    @GET("themes")
    Observable<ThemeList> loadThemeList();

    @GET("theme/{type}")
    Observable<ThemeDaily> loadThemeDaily(@Path("type") String type);

    @GET("news/{articleId}")
    Observable<NewsDetail> loadNewsContent(@Path("articleId") String articleId);

    @GET("story-extra/{articleId}")
    Observable<NewsExtra> loadNewsExtraData(@Path("articleId") String articleId);

    @GET("story/{articleId}/long-comments")
    Observable<CommentList> loadLongCommentData(@Path("articleId") String articleId);

    @GET("story/{articleId}/short-comments")
    Observable<CommentList> loadShortCommentData(@Path("articleId") String articleId);
}
