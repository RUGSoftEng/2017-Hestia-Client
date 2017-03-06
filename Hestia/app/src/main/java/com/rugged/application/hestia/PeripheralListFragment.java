package com.rugged.application.hestia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PeripheralListFragment extends Fragment {

    private RecyclerView myPeripheralRecyclerView;
    private PeripheralAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_peripheral_list, container, false);

        myPeripheralRecyclerView = (RecyclerView) view.findViewById(R.id.peripheral_recycler_view);
        myPeripheralRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private class PeripheralHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        public TextView mTitleTextView;
        private Peripheral mPeripheral;

        public PeripheralHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView;
        }
        public void bindPeripheral(Peripheral p) {
            mPeripheral = p;
            mTitleTextView.setText(mPeripheral.getId() + ". " + mPeripheral.getType());

        }


        @Override
        public void onClick(View view) {
            Intent intent = PeripheralActivity.newIntent(getActivity(), mPeripheral.getId());
            startActivity(intent);

        }
    }

    private class PeripheralAdapter extends RecyclerView.Adapter<PeripheralHolder> {

        private List<Peripheral> mPeripherals;

        public PeripheralAdapter(List<Peripheral> peripherals){
            mPeripherals = peripherals;
        }

        @Override
        public PeripheralHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            //simple_list_item can be changed to custom item layout (page 188 of book)
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new PeripheralHolder(view);
        }

        @Override
        public void onBindViewHolder(PeripheralHolder holder, int position) {
            Peripheral peripheral = mPeripherals.get(position);
            holder.bindPeripheral(peripheral);
        }

        @Override
        public int getItemCount() {
            return mPeripherals.size();
        }
    }

    private void updateUI() {
        PeripheralLab peripheralLab = PeripheralLab.get(getActivity());
        List<Peripheral> peripherals = peripheralLab.getPeripherals();

        mAdapter = new PeripheralAdapter(peripherals);
        myPeripheralRecyclerView.setAdapter(mAdapter);
    }








}
