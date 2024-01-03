import {helpers, maxLength, required} from "@vuelidate/validators";

export const fieldRequired = helpers.withMessage('Este campo es obligatorio', required);
export const fieldMaxLength = (max: number) => helpers.withMessage(`Limite de ${max} caracteres permitidos`, maxLength(max));