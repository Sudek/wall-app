package ikko_ikki.wall_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class PartThreeFragment extends Fragment {

    public final static String TITLE_KEY = "titles";
    public final static String LINK_KEY = "links";

    public static PartThreeFragment createInstance
            (ArrayList<String> titleList, ArrayList<String> linksList) {
        PartThreeFragment partThreeFragment = new PartThreeFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(TITLE_KEY, titleList);
        bundle.putStringArrayList(LINK_KEY, linksList);
        partThreeFragment.setArguments(bundle);
        return partThreeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_part_three, container, false);
        setupRecyclerView(recyclerView);
        return recyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        Bundle bundle = getArguments();
        ArrayList<String> titles = bundle.getStringArrayList(TITLE_KEY);
        ArrayList<String> links = bundle.getStringArrayList(LINK_KEY);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(titles, links);
        recyclerView.setAdapter(recyclerAdapter);
    }

}