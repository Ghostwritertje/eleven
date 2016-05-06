package be.ghostwritertje.budgetting.wicket.pages;

import be.ghostwritertje.budgetting.wicket.panels.DoorlopendStatementForm;

/**
 * Created by jorandeboever
 * on 6/05/16.
 */
public class DoorlopendStatementPage extends WicketPage {

    public DoorlopendStatementPage() {
        this.add(new DoorlopendStatementForm("statementForm"));
    }
}
