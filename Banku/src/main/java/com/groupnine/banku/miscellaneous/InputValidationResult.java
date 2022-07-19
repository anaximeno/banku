package com.groupnine.banku.miscellaneous;

public class InputValidationResult {
    public boolean isValid;
    public String explainStatus;

    public InputValidationResult(boolean isValid, String explainStatus) {
        this.isValid = isValid;
        this.explainStatus = explainStatus;
    }

    public InputValidationResult(boolean isValid) {
        this(isValid, isValid ? "Operação efetuada com sucesso." : "Operação efetuada sem sucesso.");
    }
}
