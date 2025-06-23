/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.jgoose.model;

import br.unioeste.jgoose.UseCases.UseCase;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alysson Girotto
 */
public class UCActor extends UCElement{
    
    private String code;
    private String bpmnElementoCode;
    private String name;
    private List<UCUseCase> useCases; 
    private UCActor father;
    private List<UCActor> children;

    public UCActor() {
        useCases = new ArrayList<>();
        father = null;
        children = new ArrayList<>();        
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    public String getBpmnElementoCode() {
        return bpmnElementoCode;
    }

    public void setBpmnElementoCode(String bpmnElementoCode) {
        this.bpmnElementoCode = bpmnElementoCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UCUseCase> getUseCases() {
        return useCases;
    }

    public void setUseCases(List<UCUseCase> useCases) {
        this.useCases = useCases;
    }

    public void addUseCase(UCUseCase useCase){
        this.useCases.add(useCase);
    }
    
    public UCActor getFather() {
        return father;
    }

    public void setFather(UCActor father) {
        this.father = father;
    }

    public List<UCActor> getChildren() {
        return children;
    }

    public void setChildren(List<UCActor> children) {
        this.children = children;
    }

    public void addChild(UCActor child){
        children.add(child);
    }
    
    @Override
    public String toString() {
        return "\nUCActor{" + 
                    "\tcod=" + code + 
                    "\tbpmn cod=" + bpmnElementoCode + 
                    "\tname=" + name + 
                    "\t\tuseCases=" + useCases +
                    "\t\tchildren=" + children +
                '}';
    }
    
    
    
}
