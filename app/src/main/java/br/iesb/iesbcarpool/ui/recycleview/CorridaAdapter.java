package br.iesb.iesbcarpool.ui.recycleview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.iesb.iesbcarpool.R;
import br.iesb.iesbcarpool.ui.Model.Corrida;
import br.iesb.iesbcarpool.ui.Model.Usuario;

import static android.support.constraint.Constraints.TAG;

public class CorridaAdapter extends RecyclerView.Adapter<CorridaAdapter.ViewHolderCorrida> {

    private List<Corrida> dados = new ArrayList<>();
    private Context mContext;

    public CorridaAdapter(List<Corrida> dados, Context mContext) {
        this.dados = dados;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CorridaAdapter.ViewHolderCorrida onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.corrida_historico, parent, false);

        ViewHolderCorrida holder = new ViewHolderCorrida(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CorridaAdapter.ViewHolderCorrida holder, int position) {
        Log.d(TAG, "onBindViewHolder: chamado");

        if (dados != null && dados.size() > 0){
            Corrida corrida = dados.get(position);
            holder.iesbDestino.setText(corrida.getIesb());
            holder.motorista.setText(corrida.getNomeMotorista());
            holder.horarioPartida.setText((String.valueOf(corrida.getHoraPartida())));
            holder.horarioChegada.setText((String.valueOf(corrida.getHoraDestino())));
        }
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public class ViewHolderCorrida extends RecyclerView.ViewHolder{
        public TextView iesbDestino;
        public TextView motorista;
        public TextView horarioPartida;
        public TextView horarioChegada;
        ConstraintLayout historicoLayout;

        public ViewHolderCorrida(@NonNull View itemView) {
            super(itemView);

            iesbDestino = itemView.findViewById(R.id.idRecycleDestino);
            motorista = itemView.findViewById(R.id.idRecycleMotorista);
            horarioPartida = itemView.findViewById(R.id.idRecycleHorarioPartida);
            horarioChegada = itemView.findViewById(R.id.idRecycleHorarioChegada);
            historicoLayout = itemView.findViewById(R.id.historico_layout);
        }
    }

}
