package com.opencars.netgo.powerbiembedded.model;

import com.opencars.netgo.powerbiembedded.controller.EmbedController;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportConfig {

    static final Logger logger = LoggerFactory.getLogger(EmbedController.class);

    public String reportId = "";

    public String embedUrl = "";

    public String reportName = "";

    public Boolean isEffectiveIdentityRolesRequired = false;

    public Boolean isEffectiveIdentityRequired = false;

    public Boolean enableRLS = false;

    public String username;

    public String roles;

    public JSONObject getJSONObject() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("reportId", reportId);
            jsonObj.put("embedUrl", embedUrl);
            jsonObj.put("reportName", reportName);
        } catch (Exception e) {
            logger.error("DefaultListItem.toString JSONException: " + e.getMessage());
        }
        return jsonObj;
    }

}
