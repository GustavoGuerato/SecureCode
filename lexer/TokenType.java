public enum TokenType {
    // keywords
    AUDITORIA, DEFINIR, EXECUTAR, REGRA, NIVEL, SE, ACAO, FIM,

    // logical
    AND, OR,

    // priority
    BAIXA, MEDIA, ALTA, CRITICA,

    // actions
    REJEITAR, NOTIFICAR, SOLICITAR_MFA, LOG_ERROR,

    // operators
    CONTAINS, EQUALS, MATCHES, NOT,

    // symbols
    IDENTIFIER, STRING,

    EOF
}