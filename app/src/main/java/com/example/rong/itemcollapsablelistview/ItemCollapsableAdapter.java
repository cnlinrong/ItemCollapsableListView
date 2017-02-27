package com.example.rong.itemcollapsablelistview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rong on 2017/2/26.
 */

public class ItemCollapsableAdapter extends BaseAdapter {

    private Context mContext;

    private List<String> data = new ArrayList<>();

    private ViewHelper lastExpandedViewHelper;

    private int lastExpandedPosition = -1;

    public ItemCollapsableAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View rootView = View.inflate(mContext, R.layout.item_collapsable, null);

        ViewHelper viewHelper = new ViewHelper(rootView, position);

        String text = data.get(position);
        viewHelper.content.setText(text);
        return rootView;
    }

    class ViewHelper {

        View rootView;
        LinearLayout content_box;
        LinearLayout collapsable_box;
        View divider_line;
        TextView content;
        TextView btn_cancel;

        boolean flag_collapsed = true;
        boolean firstClicked = true;
        int maxHeight;
        int minHeight;

        ViewHelper(final View rootView, final int position) {
            this.rootView = rootView;
            this.content_box = (LinearLayout) rootView.findViewById(R.id.content_box);
            this.collapsable_box = (LinearLayout) rootView.findViewById(R.id.collapsable_box);
            this.divider_line = rootView.findViewById(R.id.divider_line);
            this.content = (TextView) rootView.findViewById(R.id.content);
            this.btn_cancel = (TextView) rootView.findViewById(R.id.btn_cancel);
            this.btn_cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "取消下载-->" + (position + 1), Toast.LENGTH_SHORT).show();
                }

            });

            if (position == lastExpandedPosition) {
                this.divider_line.setVisibility(View.VISIBLE);
                this.collapsable_box.setVisibility(View.VISIBLE);
                this.flag_collapsed = false;
                lastExpandedViewHelper = this;
            }

            content_box.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (firstClicked) {
                        collapsable_box.setVisibility(View.VISIBLE);
                        divider_line.setVisibility(View.VISIBLE);

                        firstClicked = false;
                    }

                    if (flag_collapsed) {
                        expand();

                        lastExpandedPosition = position;
                    } else {
                        collapse();

                        lastExpandedPosition = -1;
                    }

                    if (lastExpandedViewHelper != null && lastExpandedViewHelper != ViewHelper.this) {
                        lastExpandedViewHelper.collapse();
                    }
                    lastExpandedViewHelper = ViewHelper.this;
                }

            });

            init();
        }

        void init () {
            content_box.measure(0, 0);
            minHeight = content_box.getMeasuredHeight();

            collapsable_box.measure(0, 0);
            int collapsableBoxHeight = collapsable_box.getMeasuredHeight();

            divider_line.measure(0, 0);
            int dividerLineHeight = divider_line.getMeasuredHeight();

            maxHeight = minHeight + collapsableBoxHeight + dividerLineHeight;
        }

        void expand() {
            if (!flag_collapsed) {
                return;
            }

            animate(minHeight, maxHeight, rootView, 300);

            flag_collapsed = !flag_collapsed;
        }

        void collapse() {
            if (flag_collapsed) {
                return;
            }

            animate(maxHeight, minHeight, rootView, 300);

            flag_collapsed = !flag_collapsed;
        }

        void animate(int from, int to, final View targetView, int duration) {
            ValueAnimator valueAnimator = ValueAnimator.ofInt(from, to);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int animatedValue = (int) animation.getAnimatedValue();
                    targetView.getLayoutParams().height = animatedValue;
                    targetView.requestLayout();
                }

            });
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.setDuration(duration);
            valueAnimator.start();
        }

    }

}
