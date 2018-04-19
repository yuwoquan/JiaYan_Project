package com.example.administrator.jiayan_project.ui.fragment.main;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.jiayan_project.MyApplication;
import com.example.administrator.jiayan_project.R;
import com.example.administrator.jiayan_project.adapter.adapter.AddressListAdapter;
import com.example.administrator.jiayan_project.db.bean.AddressBean;
import com.example.administrator.jiayan_project.db.bean.AddressBeanDao;
import com.example.administrator.jiayan_project.db.greendao.GreenDaoManager;
import com.example.administrator.jiayan_project.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ClassifyFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private AddressListAdapter addressListAdapter =new AddressListAdapter(new ArrayList<AddressBean>());
    private List<AddressBean> addressBeans=new ArrayList<>();
    private static final String TAG = "ClassifyFragment";
    private  List<AddressBean> list;
    @Override
    protected View onCreateView() {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_classify, null);
        ButterKnife.bind(this, layout);
        LinearLayoutManager layoutManager=new LinearLayoutManager(MyApplication.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(addressListAdapter);
//
//        AddressBeanDao addressBeanDao=GreenDaoManager.getInstance().getSession().getAddressBeanDao();
//        AddressBean addressBean=new AddressBean();
//        addressBean.setName("广州市");
//        addressBean.setCheck(false);
//        addressBean.setNumber("打架好");
//        addressBean.setAddress("哒哒哒好");
//        addressBeanDao.insert(addressBean);
//        Toast.makeText(MyApplication.getContext(), "1", Toast.LENGTH_SHORT).show();

//        AddressBeanDao b=GreenDaoManager.getInstance().getSession().getAddressBeanDao();
//        b.deleteByKey(Long.valueOf("1"));
//        b.deleteByKey(Long.valueOf("2"));
//        b.deleteByKey(Long.valueOf("3"));
//        b.deleteByKey(Long.valueOf("4"));
//        b.deleteByKey(Long.valueOf("5"));

       list = GreenDaoManager.getInstance().getSession().getAddressBeanDao().queryBuilder()
                .offset(0)//偏移量，相当于 SQL 语句中的 skip
                .limit(300)//只获取结果集的前 3 个数据
                .orderDesc(AddressBeanDao.Properties.Isdefault)//通过 StudentNum 这个属性进行正序排序  Desc倒序
                .build()
                .list();
        for (int i = 0; i < list.size(); i++) {
            Log.d("zoneLog", "studentNumber:-- " +list.get(i).getId()+"--"+list.get(i).getUsername());
        }
//        Log.e(TAG, "onCreateView: "+list.size() +list.get(i));
                        addressBeans.addAll(list);
                        addressListAdapter.setNewData(addressBeans);
                        addressListAdapter.loadMoreComplete();

        addressListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {

                //判断id
                if (view.getId() == R.id.check) {
                    final CheckBox checkBox=view.findViewById(R.id.check);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            List<AddressBean> lisu =GreenDaoManager.getInstance().getSession().getAddressBeanDao().queryBuilder().build().list();

                        }
                    });
                }
            }
        });
        addressListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.e(TAG, "onItemChildClick:888888" );
            }
        });


        return layout;
    }

    @Override
    protected boolean canDragBack() {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
