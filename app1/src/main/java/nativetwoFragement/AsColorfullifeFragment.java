package nativetwoFragement;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myyushi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import bean.Listbean;
import butterknife.Bind;
import butterknife.ButterKnife;
import request.LoadingDialog;

/**
 * Created by misshu on 2017/4/19/019.
 */
public class AsColorfullifeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.ascolor)
    TextView ascolor;
    private String mParam1;
    private String mParam2;
    private Context context;
    private RequestQueue queue;
    LoadingDialog dialog;
    ImageLoader loader;
    private ArrayList<Listbean.ResponseJsonBean.ListBigBean> biglist = new ArrayList<>();
    private ArrayList<Listbean.ResponseJsonBean.ListCarouselBean> courlist = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    public AsColorfullifeFragment() {
    }

    public static AsColorfullifeFragment newInstance(String param1, String param2) {
        AsColorfullifeFragment fragment = new AsColorfullifeFragment();
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
        View view = inflater.inflate(R.layout.activity_ascolorfullife, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog = new LoadingDialog(context);
        dialog.setCanceledOnTouchOutside(false);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
