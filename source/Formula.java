public interface Formula {
    String asString();
    void setParent(Formula formula);
    Formula getParent();
    Formula rewrite();
}
