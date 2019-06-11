package br.iesb.iesbcarpool.ui.recycleview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.iesb.iesbcarpool.R;
import br.iesb.iesbcarpool.ui.model.Usuario;

public class AlunosAdapter extends RecyclerView.Adapter<AlunosAdapter.ViewHolderUsuario> {

    List<Usuario> dados;
    public AlunosAdapter(List<Usuario> dados){
        this.dados = dados;
    }
    @NonNull
    @Override
    public AlunosAdapter.ViewHolderUsuario onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.estudantes_online, viewGroup, false);

        ViewHolderUsuario holderUsuario = new ViewHolderUsuario(view);

        return holderUsuario;
    }

    @Override
    public void onBindViewHolder(@NonNull AlunosAdapter.ViewHolderUsuario viewHolder, int i) {
        if (dados != null && dados.size() > 0){
            Usuario usuario = dados.get(i);
            viewHolder.txtNome.setText(usuario.nome);
            viewHolder.txtMatricula.setText(String.valueOf(usuario.matricula));
            viewHolder.txtSexo.setText(usuario.sexo.toString());
            viewHolder.txtDistancia.setText(usuario.email);
        }
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }
    public class ViewHolderUsuario extends RecyclerView.ViewHolder {


        public TextView txtNome;
        public TextView txtMatricula;
        public TextView txtSexo;
        public TextView txtDistancia;

        public ViewHolderUsuario(@NonNull View itemView) {
            super(itemView);

            txtNome = itemView.findViewById(R.id.idRecycleNome);
            txtMatricula = itemView.findViewById(R.id.idRecycleMatricula);
            txtSexo = itemView.findViewById(R.id.idRecycleSexo);
            txtDistancia = itemView.findViewById(R.id.idRecycleDistancia);
        }
    }
}
