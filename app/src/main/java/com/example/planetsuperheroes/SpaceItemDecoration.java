package com.example.planetsuperheroes;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int space; // Espacio entre elementos
    private final int spanCount; // Número de columnas

    // Constructor que recibe el espacio y el número de columnas
    public SpaceItemDecoration(int space, int spanCount) {
        this.space = space; // Inicializa el espacio
        this.spanCount = spanCount; // Inicializa el número de columnas
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // Obtiene la posición del elemento
        int column = position % spanCount; // Determina la columna del elemento

        // Espacio a la izquierda y derecha
        outRect.left = column * space; // Espacio a la izquierda
        outRect.right = space - (column * space); // Espacio a la derecha

        // Espacio inferior
        outRect.bottom = space; // Aplica espacio en la parte inferior

        // Espacio superior solo si es el primer elemento de la fila
        if (position < spanCount) {
            outRect.top = space; // Espacio superior para la primera fila
        } else {
            outRect.top = 0; // Sin espacio superior para los demás elementos
        }
    }
}
