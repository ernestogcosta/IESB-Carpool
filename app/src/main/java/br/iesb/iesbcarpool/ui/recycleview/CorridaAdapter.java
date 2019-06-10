package br.iesb.iesbcarpool.ui.recycleview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.iesb.iesbcarpool.R;
import br.iesb.iesbcarpool.ui.Model.Corrida;
import br.iesb.iesbcarpool.ui.Model.Usuario;

public class CorridaAdapter extends RecyclerView.Adapter<CorridaAdapter.ViewHolderCorrida> {

    List<Corrida> dados;

    public CorridaAdapter(List<Corrida> dados) {
        this.dados = dados;
    }

    @NonNull
    @Override
    public CorridaAdapter.ViewHolderCorrida onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.corrida_historico, viewGroup, false);

        ViewHolderCorrida holderCorrida = new ViewHolderCorrida(view);

        return holderCorrida;

    }

    @Override
    public void onBindViewHolder(@NonNull CorridaAdapter.ViewHolderCorrida viewHolder, int i) {
        if (dados != null && dados.size() > 0){
            Corrida corrida = dados.get(i);
            viewHolder.iesbDestino.setText(corrida.getIesb());
            viewHolder.motorista.setText(corrida.getNomeMotorista());
            viewHolder.horarioPartida.setText((String.valueOf(corrida.getHoraPartida())));
            viewHolder.horarioChegada.setText((String.valueOf(corrida.getHoraDestino())));
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

        public ViewHolderCorrida(@NonNull View itemView) {
            super(itemView);

            iesbDestino = itemView.findViewById(R.id.idRecycleDestino);
            motorista = itemView.findViewById(R.id.idRecycleMotorista);
            horarioPartida = itemView.findViewById(R.id.idRecycleHorarioPartida);
            horarioChegada = itemView.findViewById(R.id.idRecycleHorarioChegada);
        }
    }
}
