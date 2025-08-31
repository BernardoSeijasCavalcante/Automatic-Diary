package br.com.automaticdiary.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import br.com.automaticdiary.MainActivity;
import br.com.automaticdiary.R;
import br.com.automaticdiary.databinding.FragmentHomeBinding;
import br.com.automaticdiary.ui.register_fragments.RegisterActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        AppCompatButton registerActivityButton = root.findViewById(R.id.registerActivity);

        registerActivityButton.setOnClickListener(v -> {
            changeFragment(new RegisterActivity());
        });

        return root;
    }

    public void changeFragment(Fragment fragment){
        FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.nav_host_fragment_activity_main, fragment);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}