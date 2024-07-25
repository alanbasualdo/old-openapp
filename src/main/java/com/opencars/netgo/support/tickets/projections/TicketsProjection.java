package com.opencars.netgo.support.tickets.projections;

import com.opencars.netgo.support.tickets.entity.*;
import com.opencars.netgo.users.entity.User;

import java.time.LocalDateTime;

public interface TicketsProjection {
    Long getId();
    String getTitle();
    TicketsCategories getCategory();
    TicketsSubcategories getSubCategory();
    TicketsSubSubCategories getSubSubCategory();
    TicketsStates getState();
    LocalDateTime getOpeningDate();
    LocalDateTime getEditDate();
    LocalDateTime getClosingDate();
    User getResolved();
    User getApplicant();
    TicketsNewEntry getNewEntry();
    TicketsLowAccounts getLowAccount();

}
