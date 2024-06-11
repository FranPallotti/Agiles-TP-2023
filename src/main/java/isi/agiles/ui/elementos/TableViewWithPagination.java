package isi.agiles.ui.elementos;

import javafx.collections.FXCollections;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;

public class TableViewWithPagination<T> {
    private Page<T> page;
    private TableView<T> tableView;
    private Pagination paginationPane;

    public TableViewWithPagination(Page<T> page, TableView<T> tableView, Pagination paginationPane) {
        this.page = page;
        this.tableView = tableView;
        this.paginationPane = paginationPane;
        paginationPane.pageCountProperty().bindBidirectional(page.totalPageProperty());
        updatePagination();
    }

    public Page<T> getPage() {
        return page;
    }

    public TableView<T> getTableView() {
        return tableView;
    }

    public Pagination getPaginationPane() {
        return paginationPane;
    }

    private void updatePagination() {
        paginationPane.setPageFactory(pageIndex -> {
            tableView.setItems(FXCollections.observableList(page.getCurrentPageDataList(pageIndex)));
            return tableView;
        });
    }
}
