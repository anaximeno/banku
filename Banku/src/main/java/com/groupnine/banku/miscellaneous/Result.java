package com.groupnine.banku.miscellaneous;

public class Result {
    public boolean isValid;
    public String explainStatus;

    public Result(boolean isValid, String explain) {
        this.isValid = isValid;
        this.explainStatus = explain;
    }

    public Result(boolean isValid) {
        this(isValid, isValid ? "Operação efetuada com sucesso." : "Operação efetuada sem sucesso.");
    }
}
