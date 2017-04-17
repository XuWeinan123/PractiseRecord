package com.xwn.practiserecord.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xwn.practiserecord.R;
import com.xwn.practiserecord.RecyclerItemClickListener;
import com.xwn.practiserecord.bean.ArticleBean;
import com.xwn.practiserecord.bean.WorksBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorksFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorksFragment extends Fragment {
    private static final String TAG = "WorksFragment";
    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView2;
    private List<WorksBean> mDatas;
    private WorkAdapter workAdapter;

    SimpleDateFormat formatter = new SimpleDateFormat ("yyyy.MM.dd");
    public WorksFragment() {
        // Required empty public constructor
    }
    public static WorksFragment newInstance() {
        WorksFragment fragment = new WorksFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_works, container, false);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView2.setAdapter(workAdapter = new WorkAdapter());
        recyclerView2.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView2, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                position = mDatas.size()-1-position;
                String fileLink = mDatas.get(position).getUrl();
                Uri uri = Uri.parse(fileLink);
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                position = mDatas.size()-1-position;
                String fileLink = mDatas.get(position).getUrl();
                Uri uri = Uri.parse(fileLink);
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);

            }
        }));

        return view;
    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        Date curDate =  new Date(System.currentTimeMillis());
        WorksBean worksBean = new WorksBean("WORK001","第一份文件",curDate,1,"https://pan.baidu.com/s/1mhMqD3E");
        BmobQuery<WorksBean> query = new BmobQuery<WorksBean>();
        query.setLimit(50);
        query.findObjects(new FindListener<WorksBean>() {
            @Override
            public void done(List<WorksBean> list, BmobException e) {
                if (e==null){
                    for (int i=0;i<list.size();i++){
                        mDatas.add(list.get(i));
                    }
                    workAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getActivity(),"您的网络状况不好"+e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.MyViewHolder>{

        @Override
        public WorkAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_works, parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(WorkAdapter.MyViewHolder holder, int position) {
            position = mDatas.size()-1-position;
            WorksBean tempWorksBean = mDatas.get(position);
            holder.worksName.setText(tempWorksBean.getWorksName());
            holder.worksCreateTime.setText(formatter.format(tempWorksBean.getWorksCreateTime()));
            switch (tempWorksBean.getWorksType()){
                case 1:
                    holder.worksImage.setImageDrawable(getResources().getDrawable(R.mipmap.img_icon));
                    break;
                case 2:
                    holder.worksImage.setImageDrawable(getResources().getDrawable(R.mipmap.doc_icon));
                    break;
                case 3:
                    holder.worksImage.setImageDrawable(getResources().getDrawable(R.mipmap.video_icon));
                    break;
                default:
                    holder.worksImage.setImageDrawable(getResources().getDrawable(R.mipmap.file_icon));

            }
            if (tempWorksBean.getWorksType()==1){
                holder.worksImage.setImageDrawable(getResources().getDrawable(R.mipmap.img_icon));
            }
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView worksImage;
            TextView worksName;
            TextView worksCreateTime;
            public MyViewHolder(View itemView) {
                super(itemView);
                worksImage = (ImageView) itemView.findViewById(R.id.works_image);
                worksName = (TextView) itemView.findViewById(R.id.works_name);
                worksCreateTime = (TextView) itemView.findViewById(R.id.works_create_time);
            }
        }
    };
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
