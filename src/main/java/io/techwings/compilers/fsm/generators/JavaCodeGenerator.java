package io.techwings.compilers.fsm.generators;

import io.techwings.compilers.fsm.OptimizedStateMachine;
import io.techwings.compilers.fsm.generators.nestedSwitchCaseGenerator.NSCNodeVisitor;
import io.techwings.compilers.fsm.implementers.JavaNestedSwitchCaseImplementer;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class JavaCodeGenerator extends CodeGenerator {
  private JavaNestedSwitchCaseImplementer implementer;

  public JavaCodeGenerator(OptimizedStateMachine optimizedStateMachine,
                           String outputDirectory,
                           Map<String, String> flags) {
    super(optimizedStateMachine, outputDirectory, flags);
    implementer = new JavaNestedSwitchCaseImplementer(flags);
  }

  protected NSCNodeVisitor getImplementer() {
    return implementer;
  }

  public void writeFiles() throws IOException {
    String outputFileName = optimizedStateMachine.header.fsm + ".java";
    Files.write(getOutputPath(outputFileName), implementer.getOutput().getBytes());
  }
}
