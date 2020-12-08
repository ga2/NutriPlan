package com.cafape.nutriplan.support;

import android.app.AlertDialog;
import android.content.Context;


/**
 * Created by IPM_WS_2 on 06/03/2017.
 */

public class AlertBuilderUtils
{
    public static AlertDialog.Builder BuildAlert(Context ctx, String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(title);
        builder.setMessage(message);
        return builder;
    }

    public static AlertDialog.Builder BuildAlert(Context ctx, int title, int message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(title);
        builder.setMessage(message);
        return builder;
    }

    public static AlertDialog.Builder BuildAlert(Context ctx, String title, int message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(title);
        builder.setMessage(message);
        return builder;
    }

    public static AlertDialog.Builder BuildAlert(Context ctx, int title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(title);
        builder.setMessage(message);
        return builder;
    }
}
