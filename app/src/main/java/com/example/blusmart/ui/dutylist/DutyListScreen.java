package com.example.blusmart.ui.dutylist;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.blusmart.R;
import com.example.blusmart.base.BaseActivity;
import com.example.blusmart.data.model.Duty;
import com.example.blusmart.utils.ViewModelFactory;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import static com.example.blusmart.utils.Constants.COMPLETED;
import static com.example.blusmart.utils.Constants.IN_PROGRESS;
import static com.example.blusmart.utils.Constants.PLANNED;

public class DutyListScreen extends BaseActivity implements DutySelectedListener {
    @BindView(R.id.recyclerView)
    RecyclerView listView;


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    ViewModelFactory viewModelFactory;
    private DutyListViewModel viewModel;

    @Override
    protected int layoutRes() {
        return R.layout.dutylist_screen;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DutyListViewModel.class);
        listView.setAdapter(new DutyListAdapter(this, viewModel, this, this));
        listView.setLayoutManager(new LinearLayoutManager(this));

        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getDutyList().observe(this, repos -> {
            if (repos != null) listView.setVisibility(View.VISIBLE);
        });

        viewModel.getError().observe(this, isError -> {
            if (isError != null) if (isError) {
                Snackbar.make(listView, getString(R.string.loading_data_error), Snackbar.LENGTH_SHORT);
            }
        });


        viewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                if (isLoading) {
                    showLoading();
                } else {
                    hideLoading();
                }
            }
        });
    }

    @Override
    public void onDutySelected(Duty duty) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.duty_update_dialog, null, false);
        RadioGroup dutyStateGroup = view.findViewById(R.id.action_group);

        builder
                .setTitle(R.string.update_duty)
                .setView(view)
                .setCancelable(true)
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .setPositiveButton(R.string.update, (dialog, which) -> {
                    AppCompatRadioButton selectedRadio = dutyStateGroup.findViewById(dutyStateGroup.getCheckedRadioButtonId());
                    if (selectedRadio != null) {
                        duty.setState(selectedRadio.getText().toString());
                        viewModel.updateDuty(duty);
                    }

                });

        dutyStateGroup.clearCheck();
        switch (duty.getState()) {
            case PLANNED:
                dutyStateGroup.findViewById(R.id.action_planned).setEnabled(true);
                dutyStateGroup.findViewById(R.id.action_in_progress).setEnabled(true);
                dutyStateGroup.findViewById(R.id.action_completed).setEnabled(false);
                dutyStateGroup.check(R.id.action_planned);
                break;

            case IN_PROGRESS:
                dutyStateGroup.findViewById(R.id.action_planned).setEnabled(false);
                dutyStateGroup.findViewById(R.id.action_in_progress).setEnabled(true);
                dutyStateGroup.findViewById(R.id.action_completed).setEnabled(true);
                dutyStateGroup.check(R.id.action_in_progress);
                break;

            case COMPLETED:
                dutyStateGroup.check(R.id.action_completed);
                dutyStateGroup.findViewById(R.id.action_planned).setEnabled(false);
                dutyStateGroup.findViewById(R.id.action_in_progress).setEnabled(false);
                dutyStateGroup.findViewById(R.id.action_completed).setEnabled(true);
                break;

            default:
                dutyStateGroup.findViewById(R.id.action_planned).setEnabled(false);
                dutyStateGroup.findViewById(R.id.action_in_progress).setEnabled(false);
                dutyStateGroup.findViewById(R.id.action_completed).setEnabled(false);

                break;
        }

        builder.show();

    }
}
