package com.example.flexpictures.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

//ViewModel
public class DashboardViewModel extends ViewModel {

/**
 * ViewModel is a class that is responsible for preparing and managing the data for
 * an {@link android.app.Activity Activity} or a {@link androidx.fragment.app.Fragment Fragment}.
 * It also handles the communication of the Activity / Fragment with the rest of the application
 * (e.g. calling the business logic classes).
 * <p>
 * A ViewModel is always created in association with a scope (an fragment or an activity) and will
 * be retained as long as the scope is alive. E.g. if it is an Activity, until it is
 * finished.
 * <p>
 * In other words, this means that a ViewModel will not be destroyed if its owner is destroyed for a
 * configuration change (e.g. rotation). The new instance of the owner will just re-connected to the
 * existing ViewModel.
 * <p>
 * The purpose of the ViewModel is to acquire and keep the information that is necessary for an
 * Activity or a Fragment. The Activity or the Fragment should be able to observe changes in the
 * ViewModel. ViewModels usually expose this information via {@link LiveData} or Android Data
 * Binding. You can also use any observability construct from you favorite framework.
 * <p>
 * ViewModel's only responsibility is to manage the data for the UI. It <b>should never</b> access
 * your view hierarchy or hold a reference back to the Activity or the Fragment.
 * <p>
 *
 * ViewModels can also be used as a communication layer between different Fragments of an Activity.
 *  Each Fragment can acquire the ViewModel using the same key via their Activity. This allows
 *  communication between Fragments in a de-coupled fashion such that they never need to talk to
 *  the other Fragment directly.
 *  <pre>
 *  public class MyFragment extends Fragment {
 *      public void onStart() {
 *          UserModel userModel = new ViewModelProvider(requireActivity()).get(UserModel.class);
 *      }
 *  }
 *  <pre>
 *
 **/
    private MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}