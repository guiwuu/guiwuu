package com.guiwuu.jpractice.ood.container.ioc.guice;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.guiwuu.jpractice.ood.ood.dip.DisplayWriter;
import com.guiwuu.jpractice.ood.ood.dip.KeyboardReader;
import com.guiwuu.jpractice.ood.ood.dip.Reader;
import com.guiwuu.jpractice.ood.ood.dip.Writer;

public class CopyModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(Reader.class).to(KeyboardReader.class);
        binder.bind(Writer.class).to(DisplayWriter.class);
    }
}
