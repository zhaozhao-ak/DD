package com.zz.ak.demo.ui.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zz.ak.demo.R;
import com.zz.ak.demo.bean.PersonMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RJYX on 2017/11/8.
 */

public class TextListAdapter extends RecyclerView.Adapter<TextListAdapter.TextHolder> {
    private Activity mContent;

    private final int MAX_LINE_COUNT = 3;

    private final int STATE_UNKNOW = -1;

    private final int STATE_NOT_OVERFLOW = 1;//文本行数不能超过限定行数

    private final int STATE_COLLAPSED = 2;//文本行数超过限定行数，进行折叠

    private final int STATE_EXPANDED = 3;//文本超过限定行数，被点击全文展开

    private SparseArray<Integer> mTextStateList;

    private List<PersonMsg> personMsgList = new ArrayList<>();

    public TextListAdapter(Activity context,List<PersonMsg> personMsgs) {
        mContent = context;
        this.personMsgList = personMsgs;
        mTextStateList = new SparseArray<>();
    }

    public void upData(List<PersonMsg> personMsgs){
        if (this.personMsgList ==null){
            this.personMsgList = new ArrayList<>();
        }
        this.personMsgList.clear();
        this.personMsgList.addAll(personMsgs);
        notifyDataSetChanged();
    }

    @Override
    public TextHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TextHolder(mContent.getLayoutInflater().inflate(R.layout.list_new_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final TextHolder holder,final int position) {
        PersonMsg personMsg = personMsgList.get(position);
//        holder.hend.setText(position+1+"");//设置头部的文字
        holder.name.setText(personMsg.getName());//设置名称
        int state=mTextStateList.get(position,STATE_UNKNOW);
//      如果该itme是第一次初始化，则取获取文本的行数
        if (state==STATE_UNKNOW){
            holder.content.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
//          这个回掉会调用多次，获取玩行数后记得注销监听
                    holder.content.getViewTreeObserver().removeOnPreDrawListener(this);
//          holder.content.getViewTreeObserver().addOnPreDrawListener(null);
//          如果内容显示的行数大于限定显示行数
                    if (holder.content.getLineCount()>MAX_LINE_COUNT) {
                        holder.content.setMaxLines(MAX_LINE_COUNT);//设置最大显示行数
                        holder.expandOrCollapse.setVisibility(View.VISIBLE);//让其显示全文的文本框状态为显示
                        holder.expandOrCollapse.setText("全文");//设置其文字为全文
                        mTextStateList.put(position, STATE_COLLAPSED);
                    }else{
                        holder.expandOrCollapse.setVisibility(View.GONE);//显示全文隐藏
                        mTextStateList.put(position,STATE_NOT_OVERFLOW);//让其不能超过限定的行数
                    }
                    return true;
                }
            });

            holder.content.setMaxLines(Integer.MAX_VALUE);//设置文本的最大行数，为整数的最大数值
            holder.content.setText(personMsg.getPersonMsg());//用Util中的getContent方法获取内容

        }else{
//      如果之前已经初始化过了，则使用保存的状态，无需在获取一次
            switch (state){
                case STATE_NOT_OVERFLOW:
                    holder.expandOrCollapse.setVisibility(View.GONE);
                    break;
                case STATE_COLLAPSED:
                    holder.content.setMaxLines(MAX_LINE_COUNT);
                    holder.expandOrCollapse.setVisibility(View.VISIBLE);
                    holder.expandOrCollapse.setText("全文");
                    break;
                case STATE_EXPANDED:
                    holder.content.setMaxLines(Integer.MAX_VALUE);
                    holder.expandOrCollapse.setVisibility(View.VISIBLE);
                    holder.expandOrCollapse.setText("收起");
                    break;
            }
            holder.content.setText(personMsg.getPersonMsg());

        }
        if (personMsg.getUpdatedAt()!=null){
            holder.time.setText(personMsg.getUpdatedAt());//设置时间
        }

        if (!TextUtils.isEmpty(personMsg.getPic())){
            Glide.with(mContent).load(personMsg.getPic())
                    .placeholder(R.mipmap.ic_cat) //设置占位图，在加载之前显示
                    .error(R.mipmap.ic_cat) //在图像加载失败时显示
                    .into(holder.hend);
        }else {
            Glide.with(mContent).load(R.mipmap.ic_cat)
                    .error(R.mipmap.ic_cat) //在图像加载失败时显示
                    .into(holder.hend);
        }

//    设置显示和收起的点击事件
        holder.expandOrCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state=mTextStateList.get(position,STATE_UNKNOW);
                if (state==STATE_COLLAPSED){
                    holder.content.setMaxLines(Integer.MAX_VALUE);
                    holder.expandOrCollapse.setText("收起");
                    mTextStateList.put(position,STATE_EXPANDED);
                }else if (state==STATE_EXPANDED){
                    holder.content.setMaxLines(MAX_LINE_COUNT);
                    holder.expandOrCollapse.setText("全文");
                    mTextStateList.put(position,STATE_COLLAPSED);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return personMsgList==null?0:personMsgList.size();
    }


    public class TextHolder extends RecyclerView.ViewHolder {
        public ImageView hend;
        public TextView name;
        public TextView content;
        public TextView time;
        public TextView expandOrCollapse;


        public TextHolder(View itemView) {
            super(itemView);
//      绑定xml布局中的控件
            time = itemView.findViewById(R.id.time_tv);
            hend = (ImageView) itemView.findViewById(R.id.tv_hend);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            content = (TextView) itemView.findViewById(R.id.tv_content);
            expandOrCollapse = (TextView) itemView.findViewById(R.id.tv_expand_or_collapse);

        }
    }
}