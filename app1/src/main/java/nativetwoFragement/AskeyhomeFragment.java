package nativetwoFragement;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myyushi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import Asadapter.Askeyhomeadapter;
import Urlutil.Utils;
import bean.Listbean;
import butterknife.Bind;
import butterknife.ButterKnife;
import request.BiZhiRequest;
import request.LoadingDialog;
import util.Myapp;

/**
 * Created by misshu on 2017/4/19/019.
 */
public class AskeyhomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.search_rcv)
    RecyclerView searchRcv;
    private String mParam1;
    private String mParam2;
    private Context context;
    private RequestQueue queue;
    LoadingDialog dialog;
    ImageLoader loader;
    private Askeyhomeadapter adapter;
    private ArrayList<Listbean.ResponseJsonBean.ListBigBean> biglist = new ArrayList<>();
    private ArrayList<Listbean.ResponseJsonBean.ListCarouselBean> courlist = new ArrayList<>();
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }
    public AskeyhomeFragment() {
    }

    public static AskeyhomeFragment newInstance(String param1, String param2) {
        AskeyhomeFragment fragment = new AskeyhomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_askeyhome, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getbiglist();
        dialog = new LoadingDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void getbiglist() {
        searchRcv.setVisibility(View.VISIBLE);
        biglist.clear();
        courlist.clear();
        BiZhiRequest<Listbean> request = new BiZhiRequest<Listbean>(Listbean.class, Utils.TYPE_MORE_URL, new Response.Listener<Listbean>() {
            @Override
            public void onResponse(Listbean response) {
                if (response != null && response.getResponseJson().getList_big() != null) {
                    biglist.addAll(response.getResponseJson().getList_big());
                    courlist.addAll(response.getResponseJson().getList_carousel());
                    setadapter();
                    dialog.dismiss();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("frf", "onErrorResponse: "+error.toString());



                    }
                });
        queue.add(request);
    }

    private void setadapter() {
        loader = ((Myapp) getActivity().getApplication()).getLoader();
        adapter = new Askeyhomeadapter(getActivity(), loader,biglist,courlist);
        searchRcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchRcv.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
