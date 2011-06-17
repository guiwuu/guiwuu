package com.guiwuu.design.container.ioc.guice;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.guiwuu.design.ood.dip.DisplayWriter;
import com.guiwuu.design.ood.dip.KeyboardReader;
import com.guiwuu.design.ood.dip.Reader;
import com.guiwuu.design.ood.dip.Writer;

public class CopyModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(Reader.class).to(KeyboardReader.class);
        binder.bind(Writer.class).to(DisplayWriter.class);
    }
}
