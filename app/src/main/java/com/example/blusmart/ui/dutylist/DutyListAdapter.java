package com.example.blusmart.ui.dutylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.blusmart.R;
import com.example.blusmart.data.model.Duty;
import com.example.blusmart.utils.Constants;
import com.example.blusmart.utils.MyTimeline;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.jerryhanks.timelineview.IndicatorAdapter;
import me.jerryhanks.timelineview.TimeLineView;
import me.jerryhanks.timelineview.model.Status;

import static com.example.blusmart.utils.Constants.COMPLETED;
import static com.example.blusmart.utils.Constants.IN_PROGRESS;
import static com.example.blusmart.utils.Constants.PLANNED;

public class DutyListAdapter extends RecyclerView.Adapter<DutyListAdapter.DutyViewHolder> {


    private Context context;
    private DutySelectedListener dutySelectedListener;
    private final List<Duty> duties = new ArrayList<>();

    public DutyListAdapter(Context context, DutyListViewModel viewModel, LifecycleOwner lifecycleOwner, DutySelectedListener dutySelectedListener) {
        this.context = context;
        this.dutySelectedListener = dutySelectedListener;
        viewModel.getDutyList().observe(lifecycleOwner, duties -> {
            this.duties.clear();
            if (duties != null) {
                this.duties.addAll(duties);
                notifyDataSetChanged();
            }
        });
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public DutyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.duty_list_item, parent, false);
        return new DutyViewHolder(view, dutySelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DutyViewHolder holder, int position) {
        holder.bind(duties.get(position));
    }

    @Override
    public int getItemCount() {
        return duties.size();
    }

    @Override
    public long getItemId(int position) {
        return duties.get(position).getId();
    }

    final class DutyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.duty_assigned)
        AppCompatTextView dutyAssignedTxt;

        @BindView(R.id.duty_action)
        TimeLineView dutyActionTxt;

        @BindView(R.id.duty_type)
        AppCompatTextView dutyTypeTxt;

        IndicatorAdapter<MyTimeline> adapter;
        private Duty duty;

        DutyViewHolder(View itemView, DutySelectedListener repoSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (duty != null) {
                    repoSelectedListener.onDutySelected(duty);
                }
            });
            adapter = new IndicatorAdapter<>(new ArrayList<>(), context, (timeLine, frameLayout, i) -> {
                View view = LayoutInflater.from(context).inflate(R.layout.sample_time_line, frameLayout, false);

                ((TextView) view.findViewById(R.id.tv_title)).setText(timeLine.getTitle());
               view.findViewById(R.id.tv_content).setVisibility(View.GONE);
                return view;
            });
        }

        void bind(Duty duty) {
            this.duty = duty;
            dutyAssignedTxt.setText(duty.getAssigned());
            dutyTypeTxt.setText(duty.getType());
            List<MyTimeline> myState = new ArrayList<>();
            switch (duty.getState()) {
                case PLANNED:
                    myState.add(new MyTimeline(Status.COMPLETED, PLANNED));
                    myState.add(new MyTimeline(Status.ATTENTION, IN_PROGRESS));
                    myState.add(new MyTimeline(Status.UN_COMPLETED, COMPLETED));
                    break;

                case Constants.IN_PROGRESS:
                    myState.add(new MyTimeline(Status.COMPLETED, PLANNED));
                    myState.add(new MyTimeline(Status.COMPLETED, IN_PROGRESS));
                    myState.add(new MyTimeline(Status.ATTENTION, COMPLETED));

                    break;

                case Constants.COMPLETED:
                    myState.add(new MyTimeline(Status.COMPLETED, PLANNED));
                    myState.add(new MyTimeline(Status.COMPLETED, IN_PROGRESS));
                    myState.add(new MyTimeline(Status.COMPLETED, COMPLETED));

                    break;

                default:
                    myState.add(new MyTimeline(Status.ATTENTION, PLANNED));
                    myState.add(new MyTimeline(Status.ATTENTION, IN_PROGRESS));
                    myState.add(new MyTimeline(Status.ATTENTION, COMPLETED));

                    break;
            }

            dutyActionTxt.setIndicatorAdapter(adapter);
            adapter.swapItems(myState);
        }
    }
}