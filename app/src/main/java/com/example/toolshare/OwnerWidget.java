package com.example.toolshare;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

/**
 * Implementation of App Widget functionality.
 */
public class OwnerWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.owner_widget);

        Paper.init(context);
        String imgUrl = Paper.book().read("imgUrl");
        String city = Paper.book().read("city");
        String name = Paper.book().read("name");
        String phone = Paper.book().read("phone");
        String email = Paper.book().read("email");

        /*if(imgUrl.equals("default")) {
            Picasso.get()
                    .load(R.drawable.user_icon)
                    .into(views, R.id.img_owner_widget, new int[]{appWidgetId});
        }
        else {
            Picasso.get()
                    .load(imgUrl)
                    .into(views, R.id.img_owner_widget, new int[]{appWidgetId});
        }*/

        views.setTextViewText(R.id.tv_owner_name_widget, name);
        views.setTextViewText(R.id.tv_owner_city_widget, city);
        views.setTextViewText(R.id.tv_owner_email_widget, email);
        views.setTextViewText(R.id.tv_owner_phone_widget, phone);



        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());

        ComponentName thisWidget = new ComponentName(context.getApplicationContext(), OwnerWidget.class);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        if (appWidgetIds != null && appWidgetIds.length > 0) {
            onUpdate(context, appWidgetManager, appWidgetIds);
        }

        super.onReceive(context, intent);
    }
}

