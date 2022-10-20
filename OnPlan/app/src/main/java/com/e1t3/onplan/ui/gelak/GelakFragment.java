package com.e1t3.onplan.ui.gelak;

import static android.content.ContentValues.TAG;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.e1t3.onplan.R;
import com.e1t3.onplan.databinding.FragmentSalasBinding;
import com.e1t3.onplan.model.Gela;
import com.e1t3.onplan.shared.Values;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class GelakFragment extends Fragment {

    private FragmentSalasBinding binding;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ListView lvGelak;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GelakViewModel salasViewModel =
                new ViewModelProvider(this).get(GelakViewModel.class);

        binding = FragmentSalasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        lvGelak = root.findViewById(R.id.lvGelak);

        listaGelak(lvGelak);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void listaGelak(ListView rvGelakList) {
        db.collection(Values.GELAK)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Gela gela =  new Gela(document);
                                lvGelak.setTooltipText(lvGelak.getTooltipText() + "\n" + gela.toString());
                                Log.d(TAG, "Dena ondo: ", task.getException());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}