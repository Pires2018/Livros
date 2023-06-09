package pt.ipg.livros

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQuery
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaLivros(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_TITULO TEXT NOT NULL, $CAMPO_ISBN TEXT, $CAMPO_DATA_PUB INTEGER, $CAMPO_FK_CATEGORIA INTEGER NOT NULL, FOREIGN KEY ($CAMPO_FK_CATEGORIA) REFERENCES ${TabelaCategorias.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    override fun consulta(
        colunas: Array<String>,
        selecao: String?,
        argsSelecao: Array<String>?,
        groupby: String?,
        having: String?,
        orderby: String?
    ): Cursor {
        val sql = SQLiteQueryBuilder()
        sql.tables = "$NOME_TABELA INNER JOIN ${TabelaCategorias.NOME_TABELA} ON ${TabelaCategorias.CAMPO_ID}=$CAMPO_FK_CATEGORIA"

        return sql.query(db, colunas, selecao, argsSelecao, groupby, having, orderby)
    }

    companion object {
        const val NOME_TABELA = "livros"

        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"
        const val CAMPO_TITULO = "titulo"
        const val CAMPO_ISBN = "isbn"
        const val CAMPO_DATA_PUB = "data_publicacao"
        const val CAMPO_FK_CATEGORIA = "id_categoria"
        const val CAMPO_DESC_CATEGORIA = TabelaCategorias.CAMPO_DESCRICAO

        val CAMPOS = arrayOf(CAMPO_ID, CAMPO_TITULO, CAMPO_ISBN, CAMPO_DATA_PUB, CAMPO_FK_CATEGORIA, CAMPO_DESC_CATEGORIA)
    }
}
