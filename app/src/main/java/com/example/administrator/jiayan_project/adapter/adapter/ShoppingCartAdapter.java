package com.example.administrator.jiayan_project.adapter.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.jiayan_project.MyApplication;
import com.example.administrator.jiayan_project.R;
import com.example.administrator.jiayan_project.app.ContantsName;
import com.example.administrator.jiayan_project.enity.cart.CartBean;
import com.example.administrator.jiayan_project.http.Constants;
import com.example.administrator.jiayan_project.utils.util.StringUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.vondear.rxtools.view.dialog.RxDialogEditSureCancel;
import com.vondear.rxtools.view.dialog.RxDialogSureCancel;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2018/6/7/007.
 */

public class ShoppingCartAdapter extends BaseAdapter {
    private boolean isShow = true;//是否显示编辑/完成
    private List<CartBean.DataBean> shoppingCartBeanList;
    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;
    private Context context;
    private Activity mainActivity;
    private LayoutInflater mInflater;
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    public ShoppingCartAdapter(Context context) {
        this.context = context;
    }

    public void setShoppingCartBeanList(Activity mainActivity,List<CartBean.DataBean> shoppingCartBeanList) {
        this.shoppingCartBeanList = shoppingCartBeanList;
        this.mainActivity = mainActivity;
        mInflater = LayoutInflater.from(mainActivity);
        notifyDataSetChanged();
    }
    /**
     * 单选接口
     *
     * @param checkInterface
     */
    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    /**
     * 改变商品数量接口
     *
     * @param modifyCountInterface
     */
    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    @Override
    public int getCount() {
        return shoppingCartBeanList == null ? 0 : shoppingCartBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return shoppingCartBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /**
     * 是否显示可编辑
     *
     * @param flag
     */
    public void isShow(boolean flag) {
        isShow = flag;
        notifyDataSetChanged();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_shopping_cart_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final CartBean.DataBean shoppingCartBean = shoppingCartBeanList.get(position);
        boolean choosed = shoppingCartBean.isChoosed();
        if (choosed){
            holder.ckOneChose.setChecked(true);
        }else{
            holder.ckOneChose.setChecked(false);
        }
        String attribute ="布草颜色："+ shoppingCartBean.getSname();
        if (!StringUtil.isEmpty(attribute)){
            holder.tvCommodityAttr.setText(attribute);
        }else{
            holder.tvCommodityAttr.setText(shoppingCartBean.getSname()+"");
        }
        holder.tvCommodityName.setText(shoppingCartBean.getDinnername()+"  ||  ");
        holder.tvCommodityPrice.setText("¥ "+shoppingCartBean.getPrice()+"");
        holder.tvCommodityNum.setText(" X"+shoppingCartBean.getNum()+"");
        holder.tvCommodityShowNum.setText(shoppingCartBean.getNum()+"");
        holder.people.setText("规格："+shoppingCartBean.getRen()+"人/桌");
//        Constants.BaseUrl+
        Glide.with(MyApplication.getContext()).load(Constants.BaseUrl+shoppingCartBean.getOriginalimg()).into(holder.ivShowPic);
//        ImageLoader.getInstance().displayImage(shoppingCartBean.getImageUrl(),holder.ivShowPic);
        //单选框按钮
        holder.ckOneChose.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
                            if (position == i) {// 设置已选位置
                                shoppingCartBeanList.get(i).setChoosed(true);
                            } else {
                                shoppingCartBeanList.get(i).setChoosed(false);
                            }
                        }
//                        .notifyDataSetChanged();

                        shoppingCartBean.setChoosed(((CheckBox) v).isChecked());
                        checkInterface.checkGroup(position, ((CheckBox) v).isChecked());//向外暴露接口
                    }
                }
        );
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    modifyCountInterface.onItemLayoutClick(position);
            }
        });
        //增加按钮
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountInterface.doIncrease(position, holder.tvCommodityShowNum, holder.ckOneChose.isChecked());//暴露增加接口
            }
        });
        //删减按钮
        holder.ivSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountInterface.doDecrease(position, holder.tvCommodityShowNum, holder.ckOneChose.isChecked());//暴露删减接口
            }
        });
        //删除弹窗
        holder.tvCommodityDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final RxDialogSureCancel rxDialogSureCancel = new RxDialogSureCancel(mainActivity);//提示弹窗
//                rxDialogSureCancel.getContentView().setText("确定要删除该商品吗？");
//                rxDialogSureCancel.getTitleView().setBackgroundResource(R.drawable.logoo);
//                rxDialogSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        modifyCountInterface.childDelete(position);//删除 目前只是从item中移除
//                        rxDialogSureCancel.cancel();
//                    }
//                });
//                rxDialogSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        rxDialogSureCancel.cancel();
//                    }
//                });
//                rxDialogSureCancel.show();


                new QMUIDialog.MessageDialogBuilder(mainActivity)
                        .setTitle("提示：")
                        .setMessage("确定要删除该宴席吗？")
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .addAction(0, "删除", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                modifyCountInterface.childDelete(position);//删除 目前只是从item中移除
                                dialog.dismiss();
                            }
                        })
                        .create(mCurrentDialogStyle).show();





//                AlertDialog alert = new AlertDialog.Builder(mainActivity).create();
//                alert.setTitle("操作提示");
//                alert.setMessage("您确定要将这些商品从购物车中移除吗？");
//                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                return;
//                            }
//                        });
//                alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                modifyCountInterface.childDelete(position);//删除 目前只是从item中移除
//
//                            }
//                        });
//                alert.show();
            }
        });
        //判断是否在编辑状态下
        if (isShow) {
            holder.tvCommodityName.setVisibility(View.VISIBLE);
            holder.rlEdit.setVisibility(View.GONE);
            holder.tvCommodityNum.setVisibility(View.VISIBLE);
            holder.tvCommodityDelete.setVisibility(View.GONE);
        } else {
            holder.tvCommodityName.setVisibility(View.VISIBLE);
            holder.rlEdit.setVisibility(View.VISIBLE);
            holder.tvCommodityNum.setVisibility(View.GONE);
            holder.tvCommodityDelete.setVisibility(View.VISIBLE);
        }
        return convertView;
    }
    //初始化控件
    class ViewHolder {
        ImageView ivShowPic,tvCommodityDelete;
        TextView tvCommodityName, tvCommodityAttr, tvCommodityPrice, tvCommodityNum, tvCommodityShowNum,ivSub, ivAdd,people;
        CheckBox ckOneChose;
        LinearLayout rlEdit;
        RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            relativeLayout=(RelativeLayout) itemView.findViewById(R.id.rela_layout);
            ckOneChose = (CheckBox) itemView.findViewById(R.id.ck_chose);
            ivShowPic = (ImageView) itemView.findViewById(R.id.iv_show_pic);
            ivSub = (TextView) itemView.findViewById(R.id.iv_sub);
            ivAdd = (TextView) itemView.findViewById(R.id.iv_add);
            tvCommodityName = (TextView) itemView.findViewById(R.id.tv_commodity_name);
            tvCommodityAttr = (TextView) itemView.findViewById(R.id.tv_commodity_attr);
            tvCommodityPrice = (TextView) itemView.findViewById(R.id.tv_commodity_price);
            tvCommodityNum = (TextView) itemView.findViewById(R.id.tv_commodity_num);
            tvCommodityShowNum = (TextView) itemView.findViewById(R.id.tv_commodity_show_num);
            tvCommodityDelete = (ImageView) itemView.findViewById(R.id.tv_commodity_delete);
            rlEdit = (LinearLayout) itemView.findViewById(R.id.rl_edit);
            people=(TextView) itemView.findViewById(R.id.people);
        }
    }
    /**
     * 复选框接口
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param position  元素位置
         * @param isChecked 元素选中与否
         */
        void checkGroup(int position, boolean isChecked);
    }


    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param position      元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int position, View showCountView, boolean isChecked);

        /**
         * 删减操作
         *
         * @param position      元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doDecrease(int position, View showCountView, boolean isChecked);

        /**
         * 删除子item
         *
         * @param position
         */
        void childDelete(int position);

        void onItemLayoutClick(int position);
    }
}

