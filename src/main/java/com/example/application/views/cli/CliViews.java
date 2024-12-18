package com.example.application.views.cli;

import com.example.application.data.RestClientService;
import com.example.application.model.CliDto;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.List;
import java.util.function.Consumer;

@PageTitle("Покупатели программы")
@Route("cli")
@Menu(order = 0, icon = LineAwesomeIconUrl.FILE)
public class CliViews extends VerticalLayout {
    public CliViews(@Autowired RestClientService service) {

        final Grid<CliDto> cliGrid = new Grid<>(CliDto.class, false);
        List<CliDto> cliDtoList = service.getAllCli();
        cliGrid.setAllRowsVisible(true);

        cliGrid.addColumn(CliDto::getId).setHeader("Id").setSortable(true);
        Grid.Column<CliDto> companyNameColumn = cliGrid.addColumn(CliDto::getCompanyName).setHeader("Название компании").setSortable(true);
        cliGrid.addComponentColumn(cliDto -> createStatusIcon(cliDto.getActiveTariff()))
                .setTooltipGenerator(cliDto -> cliDto.getActiveTariff().toString())
                .setHeader("Активация");
        cliGrid.addColumn(CliDto::getContactPhone).setHeader("Контактный номер").setSortable(true);
        cliGrid.setItems(cliDtoList);
        GridListDataView<CliDto> dataView = cliGrid.setItems(cliDtoList);
        TextField searchField = new TextField();
        searchField.setWidth("50%");
        searchField.setPlaceholder("Поиск");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> dataView.refreshAll());
        add(searchField, cliGrid);

        dataView.addFilter(cli -> {
            String searchTerm = searchField.getValue().trim();

            if (searchTerm.isEmpty())
                return true;

            boolean matchesCompanyName = matchesTerm(cli.getCompanyName(),
                    searchTerm);
            boolean matchesPhoneNumber = matchesTerm(cli.getContactPhone(),
                    searchTerm);
            return matchesCompanyName || matchesPhoneNumber;
        });

    }
    private Icon createStatusIcon(Boolean status) {
        Icon icon;
        if (status) {
            icon = VaadinIcon.CHECK.create();
            icon.getElement().getThemeList().add("badge success");
        } else {
            icon = VaadinIcon.CLOSE_SMALL.create();
            icon.getElement().getThemeList().add("badge error");
        }
        icon.getStyle().set("padding", "var(--lumo-space-xs");
        return icon;
    }
    private boolean matchesTerm(String value, String searchTerm) {
        return value.toLowerCase().contains(searchTerm.toLowerCase());
    }
}
