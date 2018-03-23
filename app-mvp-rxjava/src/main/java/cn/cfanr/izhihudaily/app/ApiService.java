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

    @GET("7/prefetch-launch-images/{size}")
    Observable<Launch> loadLaunch(@Path("size") String size);

    @GET("4/news/{type}")
    Observable<NewsList> loadHomeNews(@Path("type") String type);

    @GET("4/news/before/{date}")
    Observable<NewsList> loadHomeNewsBefore(@Path("date") String date);

    @GET("4/themes")
    Observable<ThemeList> loadThemeList();

    @GET("4/theme/{themeId}")
    Observable<ThemeDaily> loadThemeDaily(@Path("themeId") String themeId);

    @GET("4/theme/{themeId}/before/{newId}")
    Observable<ThemeDaily> loadThemeDailyBefore(@Path("themeId") String themeId, @Path("newId") String newId);

    @GET("4/news/{articleId}")
    Observable<NewsDetail> loadNewsContent(@Path("articleId") String articleId);

    @GET("4/story-extra/{articleId}")
    Observable<NewsExtra> loadNewsExtraData(@Path("articleId") String articleId);

    @GET("4/story/{articleId}/long-comments")
    Observable<CommentList> loadLongCommentData(@Path("articleId") String articleId);

    @GET("4/story/{articleId}/short-comments")
    Observable<CommentList> loadShortCommentData(@Path("articleId") String articleId);
}
