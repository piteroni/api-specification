package io.github.piteroni.api.specification.validation;

import io.github.piteroni.api.specification.bind.ValidationResultTestData;

import com.greghaskins.spectrum.Spectrum;
import static com.greghaskins.spectrum.dsl.specification.Specification.*;

import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.*;

@RunWith(Spectrum.class)

public class ValidationResultTest extends ValidationResultTestData {{
  describe("検証結果を表す ValidationResult クラス", () -> {
    beforeEach(() -> {
      cause = new Exception("MyException");
      validationResult = new ValidationResult("ValidationTest", "Summary");
      validationResult.setCause(cause);
    });

    it("getTargetNameメソッドを呼ぶと、検証対象の論理名を取得できる", () -> {
      assertThat(validationResult.getTargetName()).isEqualTo("ValidationTest");
    });

    it("getSummaryメソッドを呼ぶと、検証対象の概要を取得できる", () -> {
      assertThat(validationResult.getSummary()).isEqualTo("Summary");
    });

    it("isFailedメソッドを呼ぶと、検証に失敗しているか取得出来る", () -> {
      assertThat(validationResult.isFailed()).isFalse();
    });

    it("failメソッドを呼ぶと、検証に失敗したことを記録できる", () -> {
      validationResult.fail();

      assertThat(validationResult.isFailed()).isTrue();
    });

    it("getCauseメソッドを呼ぶと、保持している例外オブジェクトを取得できる", () -> {
      assertThat(validationResult.getCause()).isEqualTo(cause);
    });

    it("setCauseメソッドを呼ぶと、保持している例外オブジェクトを更新することができる", () -> {
      cause = new Exception("NewCause");
      validationResult.setCause(cause);

      assertThat(validationResult.getCause()).isEqualTo(cause);
    });
  });
}}
