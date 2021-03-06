package com.github.andrewlord1990.materialandroid.component.grid;

/*
 *  Copyright (C) 2016 Andrew Lord
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 *  the License.
 *
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 *  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 *  See the License for the specific language governing permissions and limitations under the License.
 */

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.github.andrewlord1990.materialandroid.R;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.internal.ShadowExtractor;
import org.robolectric.res.Attribute;
import org.robolectric.res.ResourceLoader;
import org.robolectric.shadows.RoboAttributeSet;
import org.robolectric.shadows.ShadowResources;

import java.util.ArrayList;
import java.util.List;

import static com.github.andrewlord1990.materialandroid.component.grid.GridItemViewAssert.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
public class GridItemViewTest {

  private static final String PACKAGE = "com.github.andrewlord1990.materialandroid";
  private static final String PRIMARY = "somePrimaryText";
  private static final String SECONDARY = "someSecondaryText";

  private GridItemView gridItemView;

  private static ShadowResources shadowOf(Resources actual) {
    return (ShadowResources) ShadowExtractor.extract(actual);
  }

  @Before
  public void before() {
    MockitoAnnotations.initMocks(this);

    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    gridItemView = new GridItemView(RuntimeEnvironment.application);
    gridItemView.setLayoutParams(params);
    gridItemView.layout(0, 0, 500, 1000);
  }

  @Test
  public void givenContext_whenCreated_thenDefaultVariant() {
    //When
    GridItemView view = new GridItemView(RuntimeEnvironment.application);

    //Then
    assertThat(view).hasVariant(GridItemView.VARIANT_ONE_LINE_TEXT);
  }

  @Test
  public void givenAttrsWithNoCustomAttributes_whenCreated_thenSetupWithDefaults() {
    //Given
    AttributeSet attrs = createAttributeSet(new ArrayList<Attribute>());

    //When
    GridItemView view = new GridItemView(RuntimeEnvironment.application, attrs);

    //Then
    assertThat(view).hasVariant(GridItemView.VARIANT_ONE_LINE_TEXT);
  }

  @Test
  public void givenVariantInAttrs_whenCreated_thenHasVariant() {
    //Given
    List<Attribute> attributes = new ArrayList<>();
    attributes.add(createAttribute("md_grid_item_variant", "two_line_text_icon"));
    AttributeSet attrs = createAttributeSet(attributes);

    //When
    GridItemView view = new GridItemView(RuntimeEnvironment.application, attrs);

    //Then
    assertThat(view)
        .hasVariant(GridItemView.VARIANT_TWO_LINE_TEXT_ICON)
        .hasSecondaryTextView()
        .hasIconView();
  }

  @Test
  public void givenTextsInAttrs_whenCreated_thenHasTexts() {
    //Given
    List<Attribute> attributes = new ArrayList<>();
    attributes.add(createAttribute("md_grid_item_variant", "two_line_text"));
    attributes.add(createAttribute("md_grid_text_primary", PRIMARY));
    attributes.add(createAttribute("md_grid_text_secondary", SECONDARY));
    AttributeSet attrs = createAttributeSet(attributes);

    //When
    GridItemView view = new GridItemView(RuntimeEnvironment.application, attrs, 0);

    //Then
    assertThat(view)
        .hasPrimaryText(PRIMARY)
        .hasSecondaryText(SECONDARY)
        .hasSecondaryTextView();
  }

  @Test
  public void givenTextColorsInAttrs_whenCreated_thenHasTextColors() {
    //Given
    List<Attribute> attributes = new ArrayList<>();
    attributes.add(createAttribute("md_grid_item_variant", "two_line_text"));
    attributes.add(createAttribute("md_grid_text_primary_color", "@color/md_orange_50"));
    attributes.add(createAttribute("md_grid_text_secondary_color", "@color/md_light_blue_a700"));
    AttributeSet attrs = createAttributeSet(attributes);

    //When
    GridItemView view = new GridItemView(RuntimeEnvironment.application, attrs, 0, 0);

    //Then
    assertThat(view)
        .hasPrimaryTextColor(getColor(R.color.md_orange_50))
        .hasSecondaryTextColor(getColor(R.color.md_light_blue_a700));
  }

  @Test
  public void givenIconInAttrs_whenCreated_thenHasIcon() {
    //Given
    List<Attribute> attributes = new ArrayList<>();
    attributes.add(createAttribute("md_grid_item_variant", "one_line_text_icon"));
    attributes.add(createAttribute("md_grid_icon", "@drawable/ic_icon_square"));
    AttributeSet attrs = createAttributeSet(attributes);

    //When
    GridItemView view = new GridItemView(RuntimeEnvironment.application, attrs, 0);

    //Then
    assertThat(view)
        .hasIcon(R.drawable.ic_icon_square)
        .hasIconView();
  }

  @Test
  public void givenOneLineText_whenSetVariant_thenVariantChanged() {
    //When
    gridItemView.setVariant(GridItemView.VARIANT_ONE_LINE_TEXT);

    //Then
    assertThat(gridItemView)
        .hasVariant(GridItemView.VARIANT_ONE_LINE_TEXT)
        .doesNotHaveSecondaryTextView()
        .doesNotHaveIconView();
  }

  @Test
  public void givenOneLineTextIcon_whenSetVariant_thenVariantChanged() {
    //When
    gridItemView.setVariant(GridItemView.VARIANT_ONE_LINE_TEXT_ICON);

    //Then
    assertThat(gridItemView)
        .hasVariant(GridItemView.VARIANT_ONE_LINE_TEXT_ICON)
        .hasIconView()
        .doesNotHaveSecondaryTextView();
  }

  @Test
  public void givenTwoLineText_whenSetVariant_thenVariantChanged() {
    //When
    gridItemView.setVariant(GridItemView.VARIANT_TWO_LINE_TEXT);

    //Then
    assertThat(gridItemView)
        .hasVariant(GridItemView.VARIANT_TWO_LINE_TEXT)
        .hasSecondaryTextView()
        .doesNotHaveIconView();
  }

  @Test
  public void givenTwoLineTextIcon_whenSetVariant_thenVariantChanged() {
    //When
    gridItemView.setVariant(GridItemView.VARIANT_TWO_LINE_TEXT_ICON);

    //Then
    assertThat(gridItemView)
        .hasVariant(GridItemView.VARIANT_TWO_LINE_TEXT_ICON)
        .hasSecondaryTextView()
        .hasIconView();
  }

  @Test
  public void givenInvalidVariant_whenSetVariant_thenHasDefaultVariant() {
    //When
    gridItemView.setVariant(10000);

    //Then
    assertThat(gridItemView)
        .doesNotHaveSecondaryTextView()
        .doesNotHaveIconView();
  }

  @Test
  public void givenTexts_whenSetVariant_thenTextSetOnNewViews() {
    //Given
    gridItemView.setVariant(GridItemView.VARIANT_ONE_LINE_TEXT);
    gridItemView.setPrimaryText(PRIMARY);
    gridItemView.setSecondaryText(SECONDARY);

    //When
    gridItemView.setVariant(GridItemView.VARIANT_TWO_LINE_TEXT);

    //Then
    assertThat(gridItemView)
        .hasPrimaryText(PRIMARY)
        .hasSecondaryText(SECONDARY);
  }

  @Test
  public void givenTextColors_whenSetVariant_thenTextColorsSetOnNewViews() {
    //Given
    gridItemView.setVariant(GridItemView.VARIANT_ONE_LINE_TEXT);
    int expectedPrimaryColor = getColor(R.color.md_amber_100);
    gridItemView.setPrimaryTextColor(expectedPrimaryColor);
    int expectedSecondaryColor = getColor(R.color.md_blue_100);
    gridItemView.setSecondaryTextColor(expectedSecondaryColor);

    //When
    gridItemView.setVariant(GridItemView.VARIANT_TWO_LINE_TEXT);

    //Then
    assertThat(gridItemView)
        .hasPrimaryTextColor(expectedPrimaryColor)
        .hasSecondaryTextColor(expectedSecondaryColor);
  }

  @Test
  public void givenTextColorStateLists_whenSetVariant_thenTextColorStateListsSetOnNewViews() {
    //Given
    gridItemView.setVariant(GridItemView.VARIANT_ONE_LINE_TEXT);
    ColorStateList expectedPrimaryColor = createColorStateList(Color.RED, Color.CYAN);
    gridItemView.setPrimaryTextColor(expectedPrimaryColor);
    ColorStateList expectedSecondaryColor = createColorStateList(Color.BLACK, Color.GREEN);
    gridItemView.setSecondaryTextColor(expectedSecondaryColor);

    //When
    gridItemView.setVariant(GridItemView.VARIANT_TWO_LINE_TEXT);

    //Then
    assertThat(gridItemView)
        .hasPrimaryTextColor(expectedPrimaryColor)
        .hasSecondaryTextColor(expectedSecondaryColor);
  }

  @Test
  public void givenStringResource_whenSetPrimaryText_thenHasPrimaryText() {
    //When
    gridItemView.setPrimaryText(R.string.list_item_avatar_cd);

    //Then
    assertThat(gridItemView)
        .hasPrimaryText("Avatar");
  }

  @Test
  public void whenSetPrimaryText_thenHasPrimaryText() {
    //Given
    final String expectedText = "someText";

    //When
    gridItemView.setPrimaryText(expectedText);

    //Then
    assertThat(gridItemView)
        .hasPrimaryText(expectedText);
  }

  @Test
  public void givenStateList_whenSetPrimaryTextColor_thenHasPrimaryTextColor() {
    //Given
    final ColorStateList expectedColor = createColorStateList(Color.DKGRAY, Color.RED);

    //When
    gridItemView.setPrimaryTextColor(expectedColor);

    //Then
    assertThat(gridItemView)
        .hasPrimaryTextColor(expectedColor);
  }

  @Test
  public void givenColorValue_whenSetPrimaryTextColor_thenHasPrimaryTextColor() {
    //Given
    final int expectedColor = Color.BLUE;

    //When
    gridItemView.setPrimaryTextColor(expectedColor);

    //Then
    assertThat(gridItemView)
        .hasPrimaryTextColor(expectedColor);
  }

  @Test
  public void whenSetPrimaryTextColorRes_thenHasPrimaryTextColor() {
    //Given
    @ColorRes final int expectedColor = R.color.md_red_400;

    //When
    gridItemView.setPrimaryTextColorRes(expectedColor);

    //Then
    assertThat(gridItemView)
        .hasPrimaryTextColor(getColor(expectedColor));
  }

  @Test
  public void givenNoSecondaryTextView_whenGetSecondaryText_thenNull() {
    //Given
    gridItemView.setVariant(GridItemView.VARIANT_ONE_LINE_TEXT);

    //When
    CharSequence actual = gridItemView.getSecondaryText();

    //Then
    Assertions.assertThat(actual).isNull();
  }

  @Test
  public void givenStringResource_whenSetSecondaryText_thenHasSecondaryText() {
    //When
    gridItemView.setSecondaryText(R.string.list_item_icon_cd);
    gridItemView.setVariant(GridItemView.VARIANT_TWO_LINE_TEXT);

    //Then
    assertThat(gridItemView)
        .hasSecondaryText("Icon");
  }

  @Test
  public void whenSetSecondaryText_thenHasSecondaryText() {
    //Given
    final String expectedText = "someText";

    //When
    gridItemView.setSecondaryText(expectedText);
    gridItemView.setVariant(GridItemView.VARIANT_TWO_LINE_TEXT);

    //Then
    assertThat(gridItemView)
        .hasSecondaryText(expectedText);
  }

  @Test
  public void givenNoSecondaryTextView_whenGetSecondaryTextColors_thenNull() {
    //Given
    gridItemView.setVariant(GridItemView.VARIANT_ONE_LINE_TEXT);

    //When
    ColorStateList actual = gridItemView.getSecondaryTextColors();

    //Then
    Assertions.assertThat(actual).isNull();
  }

  @Test
  public void givenNoSecondaryTextView_whenGetCurrentSecondaryTextColor_thenZero() {
    //Given
    gridItemView.setVariant(GridItemView.VARIANT_ONE_LINE_TEXT);

    //When
    int actual = gridItemView.getCurrentSecondaryTextColor();

    //Then
    Assertions.assertThat(actual).isEqualTo(0);
  }

  @Test
  public void givenStateList_whenSetSecondaryTextColor_thenHasSecondarTextColor() {
    //Given
    final ColorStateList expectedColor = createColorStateList(Color.DKGRAY, Color.RED);

    //When
    gridItemView.setSecondaryTextColor(expectedColor);
    gridItemView.setVariant(GridItemView.VARIANT_TWO_LINE_TEXT);

    //Then
    assertThat(gridItemView)
        .hasSecondaryTextColor(expectedColor);
  }

  @Test
  public void givenColorValue_whenSetSecondaryTextColor_thenHasSecondaryTextColor() {
    //Given
    final int expectedColor = Color.BLUE;

    //When
    gridItemView.setSecondaryTextColor(expectedColor);
    gridItemView.setVariant(GridItemView.VARIANT_TWO_LINE_TEXT);

    //Then
    assertThat(gridItemView)
        .hasSecondaryTextColor(expectedColor);
  }

  @Test
  public void whenSetSecondaryTextColorRes_thenHasSecondaryTextColor() {
    //Given
    @ColorRes final int expectedColor = R.color.md_red_400;

    //When
    gridItemView.setSecondaryTextColorRes(expectedColor);
    gridItemView.setVariant(GridItemView.VARIANT_TWO_LINE_TEXT);

    //Then
    assertThat(gridItemView)
        .hasSecondaryTextColor(getColor(expectedColor));
  }

  @Test
  public void givenNoIconView_whenGetIcon_thenNull() {
    //Given
    gridItemView.setVariant(GridItemView.VARIANT_TWO_LINE_TEXT);

    //When
    Drawable actual = gridItemView.getIcon();

    //Then
    Assertions.assertThat(actual).isNull();
  }

  @Test
  public void givenDrawableResource_whenSetIcon_thenHasIcon() {
    //When
    gridItemView.setIcon(R.drawable.ic_icon_square);
    gridItemView.setVariant(GridItemView.VARIANT_ONE_LINE_TEXT_ICON);

    //Then
    assertThat(gridItemView).hasIcon(R.drawable.ic_icon_square);
  }

  @Test
  public void givenOneLine_whenSetIconGravity_thenHasIconGravity() {
    //Given
    gridItemView.setVariant(GridItemView.VARIANT_ONE_LINE_TEXT_ICON);

    //When
    gridItemView.setIconGravity(GridItemView.ICON_GRAVITY_END);

    //Then
    assertThat(gridItemView).hasIconGravity(GridItemView.ICON_GRAVITY_END);
  }

  @Test
  public void givenTwoLine_whenSetIconGravity_thenHasIconGravity() {
    //Given
    gridItemView.setVariant(GridItemView.VARIANT_TWO_LINE_TEXT_ICON);

    //When
    gridItemView.setIconGravity(GridItemView.ICON_GRAVITY_END);

    //Then
    assertThat(gridItemView).hasIconGravity(GridItemView.ICON_GRAVITY_END);
  }

  private Attribute createAttribute(String name, String value) {
    return new Attribute(PACKAGE + ":attr/" + name, value, PACKAGE);
  }

  private AttributeSet createAttributeSet(List<Attribute> attributes) {
    Resources resources = RuntimeEnvironment.application.getResources();
    ResourceLoader resourceLoader = shadowOf(resources).getResourceLoader();
    return new RoboAttributeSet(attributes, resourceLoader);
  }

  @ColorInt
  private int getColor(@ColorRes int color) {
    return ContextCompat.getColor(RuntimeEnvironment.application, color);
  }

  private ColorStateList createColorStateList(@ColorInt int color0, @ColorInt int color1) {
    int[] state0 = new int[] {0, 1};
    int[] state1 = new int[] {2, 3};
    int[][] states = new int[][] {state0, state1};
    int[] colors = new int[] {color0, color1};
    return new ColorStateList(states, colors);
  }

}