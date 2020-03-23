package com.timmattison.embeddedvaadin.vaadin;

import com.vaadin.flow.component.Component;
import dagger.Module;

import java.util.Set;

@Module
public interface InterfaceDaggerEmbeddedVaadinModule {
    Set<Class<? extends Component>> provideVaadinComponents();
}
