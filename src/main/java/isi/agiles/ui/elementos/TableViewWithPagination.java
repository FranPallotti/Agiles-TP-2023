package isi.agiles.ui.elementos;

import javafx.collections.FXCollections;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;

public class TableViewWithPagination<T> {
    private Page<T> page;
    private TableView<T> tableView;
    private Pagination tableViewWithPaginationPane;

    public TableViewWithPagination(Page<T> page, TableView<T> tableView) {
        this.page = page;
        this.tableView = tableView;
        tableViewWithPaginationPane = new Pagination();
        tableViewWithPaginationPane.pageCountProperty().bindBidirectional(page.totalPageProperty());
        updatePagination();
    }

    public Page<T> getPage() {
        return page;
    }

    public TableView<T> getTableView() {
        return tableView;
    }

    public Pagination getTableViewWithPaginationPane() {
        return tableViewWithPaginationPane;
    }

    private void updatePagination() {
        tableViewWithPaginationPane.setPageFactory(pageIndex -> {
            tableView.setItems(FXCollections.observableList(page.getCurrentPageDataList(pageIndex)));
            return tableView;
        });
    }
}
