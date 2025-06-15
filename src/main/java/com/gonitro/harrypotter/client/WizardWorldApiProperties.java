package com.gonitro.harrypotter.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wizard-world-api")
public class WizardWorldApiProperties {

    private String url;
    private String ingredientsPath;
    private String elixirsPath;

    public String getUrl() {
        return url;
    }

    public String getIngredientsPath() {
        return ingredientsPath;
    }

    public String getElixirsPath() {
        return elixirsPath;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setIngredientsPath(String ingredientsPath) {
        this.ingredientsPath = ingredientsPath;
    }

    public void setElixirsPath(String elixirsPath) {
        this.elixirsPath = elixirsPath;
    }
}
