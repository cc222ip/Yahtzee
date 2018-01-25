package model;

interface IVisitor {
    int visit(IVisitable o);
    int visit(Classic o);
    int visit(Regular o);
}
