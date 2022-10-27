package com.e1t3.onplan.ui.gelak;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.e1t3.onplan.R;
import com.e1t3.onplan.databinding.FragmentSalasBinding;
import com.e1t3.onplan.model.Gela;
import com.e1t3.onplan.shared.Values;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GelakFragment extends Fragment {

    private FragmentSalasBinding binding;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ListView lvGelak;
    private List<Spanned> llista = new ArrayList<>();
    private ArrayAdapter<Spanned> arrayAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GelakViewModel salasViewModel =
                new ViewModelProvider(this).get(GelakViewModel.class);

        binding = FragmentSalasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        lvGelak = root.findViewById(R.id.lvGelak);

        listaGelak();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void listaGelak() {
        db.collection(Values.GELAK)
                .orderBy(Values.GELAK_EDUKIERA)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Gela gela =  new Gela(document);
                                llista.add(Html.fromHtml(gela.toString()));
                                arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, llista);
                                lvGelak.setAdapter(arrayAdapter);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}