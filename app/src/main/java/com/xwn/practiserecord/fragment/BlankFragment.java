package com.xwn.practiserecord.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xwn.practiserecord.ArticleActivity;
import com.xwn.practiserecord.MainActivity;
import com.xwn.practiserecord.R;
import com.xwn.practiserecord.RecyclerItemClickListener;
import com.xwn.practiserecord.bean.ArticleBean;

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
 * {@link BlankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {

    private static final String TAG = "BlankFragment";
    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private List<ArticleBean> mDatas;

    SimpleDateFormat formatter = new SimpleDateFormat ("yyyy.MM.dd HH:mm:ss");

    private HomeAdapter mAdapter;

    public BlankFragment() {
        // Required empty public constructor
    }

    public static BlankFragment newInstance() {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        initDatas();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                position = mDatas.size()-1-position;
                //Toast.makeText(getActivity(),"进入了第"+position+"个页面",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra("name",mDatas.get(position).getArticleName());
                intent.putExtra("number",mDatas.get(position).getArticleNumber());
                intent.putExtra("date",formatter.format(mDatas.get(position).getArticleCreateTime()));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_home, parent,false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            position = mDatas.size()-1-position;
            holder.articleName.setText(mDatas.get(position).getArticleName());
            holder.articleCharacterNumber.setText(mDatas.get(position).getArticleCharacter()+"个字");
            String str = formatter.format(mDatas.get(position).getArticleCreateTime());
            holder.articleCreateTime.setText(str);
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            TextView articleName;
            TextView articleCharacterNumber;
            TextView articleCreateTime;
            public MyViewHolder(View view) {
                super(view);
                articleName = (TextView) view.findViewById(R.id.article_name);
                articleCharacterNumber = (TextView) view.findViewById(R.id.article_character_number);
                articleCreateTime = (TextView) view.findViewById(R.id.article_create_time);
            }
        }
    }
    private void initDatas() {
        mDatas = new ArrayList<>();
        Date curDate =  new Date(System.currentTimeMillis());
        BmobQuery<ArticleBean> query = new BmobQuery<ArticleBean>();
        query.setLimit(50);
        query.findObjects(new FindListener<ArticleBean>() {
            @Override
            public void done(List<ArticleBean> list, BmobException e) {
                if (e==null){
                    for (int i=0;i<list.size();i++){
                        mDatas.add(list.get(i));
                    }
                    mAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getActivity(),"您的网络状况不好"+e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
