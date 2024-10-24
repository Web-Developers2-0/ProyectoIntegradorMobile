package com.example.planetsuperheroes;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int space; // Variable para definir el espacio

    public SpaceItemDecoration(int space) {
        this.space = space; // Inicializa el espacio
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // Obtiene la posición del elemento
        int column = position % 2; // Asumiendo que hay 2 columnas

        outRect.left = column * space; // Aplica espacio a la izquierda
        outRect.right = space - (1 - column) * space; // Aplica espacio a la derecha

        outRect.bottom = space; // Aplica espacio en la parte inferior

        // Aplica espacio superior solo si no es el primer elemento
        if (position < 2) {
            outRect.top = space; // Espacio superior solo para los primeros dos elementos
        } else {
            outRect.top = 0; // Sin espacio superior para los demás elementos
        }
    }
}