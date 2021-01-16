package io.github.piteroni.api.specification.validation;

import io.github.piteroni.api.specification.bind.OperationCaseValidationResultTestData;

import com.greghaskins.spectrum.Spectrum;
import static com.greghaskins.spectrum.dsl.specification.Specification.*;

import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import static java.util.Arrays.asList;

@RunWith(Spectrum.class)
public class OperationCaseValidationResultTest extends OperationCaseValidationResultTestData {{
  describe("OperationCaseの検証結果を表す OperationCaseValidationResult クラス", () -> {
    beforeEach(() -> {
      validationResult = new OperationCaseValidationResult("OperationCaseValidationResultTest");
    });

    it("isFailedメソッドを呼ぶと、OperationCaseに対する検証が失敗したかを取得できる", () -> {
      assertThat(validationResult.isFailed()).isFalse();
    });

    it("failメソッドを呼ぶと、OperationCaseに対する検証が失敗したことを記録できる", () -> {
      validationResult.fail();

      assertThat(validationResult.isFailed()).isTrue();
    });

    it("getOperationCaseNameメソッドを呼ぶと、検証対象のOperationCase名を取得できる", () -> {
      assertThat(validationResult.getOperationCaseName()).isEqualTo("OperationCaseValidationResultTest");
    });

    it("getValidationResultListメソッドを呼ぶと、検証結果リストを取得できる", () -> {
      ValidationResult result1 = new ValidationResult("Validation1", "Validation1_Summary");
      ValidationResult result2 = new ValidationResult("Validation1", "Validation1_Summary");
      result2.fail();

      validationResult.addValidationResult(result1);
      validationResult.addValidationResult(result2);

      assertThat(validationResult.getValidationResultList()).isEqualTo(new ArrayList<>(asList(result1, result2)));
    });

    it("addValidationResultメソッドを呼ぶと、検証をリストに追加できる", () -> {
      ValidationResult result1 = new ValidationResult("Validation1", "Validation1_Summary");

      validationResult.addValidationResult(result1);

      assertThat(validationResult.getValidationResultList()).isEqualTo(new ArrayList<>(asList(result1)));
    });
  });
}}
