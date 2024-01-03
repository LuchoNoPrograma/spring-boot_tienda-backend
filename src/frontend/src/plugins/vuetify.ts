import {createVuetify} from 'vuetify';
import '@mdi/font/css/materialdesignicons.css';
import * as components from 'vuetify/components';
import * as directives from 'vuetify/directives';
import {es} from "vuetify/locale";
// import { BLUE_THEME} from '@/theme/LightTheme';
import {BLUE_THEME, GREEN_THEME, INDIGO_THEME, ORANGE_THEME, PURPLE_THEME, RED_THEME} from '@/theme/LightTheme';
import {
  DARK_BLUE_THEME,
  DARK_GREEN_THEME,
  DARK_INDIGO_THEME,
  DARK_ORANGE_THEME,
  DARK_PURPLE_THEME,
  DARK_RED_THEME
} from '@/theme/DarkTheme';

export default createVuetify({
  locale: {
    locale: 'es',
    fallback: 'es',
    messages: {es}
  },
  components,
  directives,

  theme: {
    defaultTheme: 'BLUE_THEME',
    themes: {
      BLUE_THEME,
      RED_THEME,
      PURPLE_THEME,
      GREEN_THEME,
      INDIGO_THEME,
      ORANGE_THEME,
      DARK_BLUE_THEME,
      DARK_RED_THEME,
      DARK_ORANGE_THEME,
      DARK_PURPLE_THEME,
      DARK_GREEN_THEME,
      DARK_INDIGO_THEME
    }
  },
  defaults: {
    VAutocomplete: {
      variant: 'outlined',
      density: 'compact',
      color: 'primary'
    },
    VCard: {
      rounded: 'md'
    },
    VCheckbox: {
      density: 'compact',
      color: 'primary'
    },
    VFileInput: {
      variant: 'outlined',
      density: 'compact',
      color: 'primary'
    },
    VListItem: {
      minHeight: '45px'
    },
    VRadio: {
      density: 'compact',
      color: 'primary'
    },
    VTextField: {
      variant: 'outlined',
      density: 'compact',
      color: 'primary'
    },
    VTextarea: {
      variant: 'outlined',
      density: 'compact',
      color: 'primary'
    },
    VSelect: {
      variant: 'outlined',
      density: 'compact',
      color: 'primary'
    },
    VTooltip: {
      location: 'top'
    }
  }
});
