export const procedureNameRule = [
    (v) => !!v || 'This field is required.',
    (v) => (v && v.length >= 4) || 'This field must contain at least 4 characters long.',
    (v) => (v && v.length <= 64) || 'This field must contain no more than 64 characters long.'
];

export const procedureDescriptionRule = [
    (v) => !!v || 'This field is required.',
    (v) => (v && v.length > 4 ) || 'This field must contain at least 4 characters long.',
    (v) => (v && v.length <= 255) || 'This field must contain no more than 255 characters long.'
];

export const procedureCostRule = [
    (v) => !!v || 'This field is required.',
    (v) => (/^\d+$/).test(v) || 'Only numbers are allowed.',
    (v) => (v && v >= 10 ) || 'The price must be more than $10.',
    (v) => (v && v <= 5000) || 'The price must be less than $5000'
];

export const procedureSpecializationRule = [
    (v) => !!v || 'This field is required.',
];

export const contentRule = [
    (v) => (v && v.length <= 1000) || 'The comment must not contain more than 1000 characters.'
];