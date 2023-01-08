package io.techwings.compilers.fsm.generators;

import io.techwings.compilers.fsm.OptimizedStateMachine;
import io.techwings.compilers.fsm.generators.nestedSwitchCaseGenerator.NSCNodeVisitor;
import io.techwings.compilers.fsm.implementers.CppNestedSwitchCaseImplementer;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class CppCodeGenerator extends CodeGenerator {
  private CppNestedSwitchCaseImplementer implementer;

  public CppCodeGenerator(OptimizedStateMachine optimizedStateMachine,
                          String outputDirectory,
                          Map<String, String> flags) {
    super(optimizedStateMachine, outputDirectory, flags);
    implementer = new CppNestedSwitchCaseImplementer(flags);
  }

  protected NSCNodeVisitor getImplementer() {
    return implementer;
  }

  public void writeFiles() throws IOException {
    String outputFileName = optimizedStateMachine.header.fsm + ".h";
    Files.write(getOutputPath(outputFileName), implementer.getOutput().getBytes());
  }
}
