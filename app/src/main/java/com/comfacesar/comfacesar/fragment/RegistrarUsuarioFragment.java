package com.comfacesar.comfacesar.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.comfacesar.Dialog.DatePickerFragment;
import com.comfacesar.comfacesar.R;
import com.comfacesar.comfacesar.Util.Util;
import com.example.extra.Calculo;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Usuario;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class RegistrarUsuarioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RegistrarUsuarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrarUsuarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrarUsuarioFragment newInstance(String param1, String param2) {
        RegistrarUsuarioFragment fragment = new RegistrarUsuarioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private static View view_permanente;
    private EditText nombreUsuarioEditText;
    private EditText apellidoEditText;
    private EditText nombreCuentaEditText;
    private EditText contraseñaCuentaEditText;
    private EditText verificarContraseñaCuentaEditText;
    private RadioButton masculinoRadioButton;
    private RadioButton femeninoRadioButton;
    //private EditText telefonoEditText;
   // private EditText direccionEditText;
    private Button registrar_usuario;
    private EditText fecha_nacimientoEditText;
    private Button tomarFotoButton;
    private Button subirFotoButton;
    private Button eliminarFotoButton;
    private Uri imageUri;
    private int PICK_IMAGE = 100;
    private int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap bitmap;
    private boolean imagen_modificada;
    private boolean imagen_eliminada = true;
    private CircleImageView fotoPerfilCircleImageView;
    private AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view_permanente = inflater.inflate(R.layout.fragment_registrar_usuario, container, false);
        nombreUsuarioEditText = view_permanente.findViewById(R.id.nombresEditTextRegistrarUsuario);
        apellidoEditText = view_permanente.findViewById(R.id.apellidosEditTextRegistrarUsuario);
        nombreCuentaEditText = view_permanente.findViewById(R.id.nombreCuentaEditTextRegistrarUsuario);
        masculinoRadioButton = view_permanente.findViewById(R.id.masculinoRadioButton);
        femeninoRadioButton = view_permanente.findViewById(R.id.femeninoRadioButton);
        contraseñaCuentaEditText = view_permanente.findViewById(R.id.contraseñaCuentaEditTextRegistrarUsuario);
        verificarContraseñaCuentaEditText = view_permanente.findViewById(R.id.verificarContraseñaCuentaEditTextRegistrarUsuario);
        registrar_usuario = view_permanente.findViewById(R.id.registrarmeButtonRegistrarUsuario);
        fecha_nacimientoEditText = view_permanente.findViewById(R.id.edadEditText);
        tomarFotoButton = view_permanente.findViewById(R.id.tomarFotoButton);
        subirFotoButton = view_permanente.findViewById(R.id.subirFotoButton);
        eliminarFotoButton = view_permanente.findViewById(R.id.eliminar_imagenButton);
        fotoPerfilCircleImageView = view_permanente.findViewById(R.id.fotoPerfilImageView);
        nombreCuentaEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    nombreCuentaEditText.setTextColor(getResources().getColor(R.color.Black));
                }
                else
                {
                    existeNombreCuenta();
                }
            }
        });
        subirFotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        tomarFotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomarFoto();
            }
        });
        eliminarFotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagen_eliminada = true;
                bitmap = null;
                fotoPerfilCircleImageView.setImageBitmap(null);
            }
        });
        fecha_nacimientoEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                fecha_nacimientoEditText.setText(fecha_nacimientoEditText.getText().toString().trim());
                if(new Calculo().validarFecha(fecha_nacimientoEditText.getText().toString()))
                {
                    fecha_nacimientoEditText.setTextColor(getResources().getColor(R.color.Black));
                }
                else
                {
                    fecha_nacimientoEditText.setTextColor(getResources().getColor(R.color.rojo));
                }
            }
        });

        registrar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar_nombre_cuenta();
            }
        });
        return view_permanente;
    }

    private void existeNombreCuenta()
    {
        String cuenta = nombreCuentaEditText.getText().toString().toLowerCase();
        HashMap<String, String> hashMap = new Gestion_usuario().existe_nombre_cuenta(cuenta);
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                if(getContext() != null)
                {
                    int val = 0;
                    try
                    {
                        val = Integer.parseInt(response);
                    }
                    catch(NumberFormatException exc)
                    {

                    }
                    if(val > 0)
                    {
                        nombreCuentaEditText.setTextColor(getResources().getColor(R.color.rojo));
                    }
                    else
                    {
                        nombreCuentaEditText.setTextColor(getResources().getColor(R.color.Black));
                    }
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                nombreCuentaEditText.setTextColor(getResources().getColor(R.color.Black));
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(view_permanente.getContext()).addToRequestQueue(stringRequest);
    }

    private void validar_nombre_cuenta()
    {
        alertDialog = new Util().getProgressDialog(view_permanente, "Registrando usuario");
        String cuenta = nombreCuentaEditText.getText().toString().toLowerCase();
        HashMap<String, String> hashMap = new Gestion_usuario().existe_nombre_cuenta(cuenta);
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                int val = 0;
                try
                {
                    val = Integer.parseInt(response);
                }
                catch(NumberFormatException exc)
                {

                }
                registrar_usuario(val == 1);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(view_permanente.getContext(),"Ha ocurrido un error en el servidor", Toast.LENGTH_LONG).show();
                alertDialog.dismiss();
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(view_permanente.getContext()).addToRequestQueue(stringRequest);
    }

    private void registrar_usuario(Boolean existe_nombre_cuenta)
    {
        if(nombreUsuarioEditText.getText().toString().isEmpty())
        {
            Toast.makeText(view_permanente.getContext(), "Ingrese su nombres", Toast.LENGTH_LONG).show();
            alertDialog.dismiss();
            return;
        }
        if(apellidoEditText.getText().toString().isEmpty())
        {
            Toast.makeText(view_permanente.getContext(), "Ingrese sus apellidos", Toast.LENGTH_LONG).show();
            alertDialog.dismiss();
            return;
        }
        if(fecha_nacimientoEditText.getText().toString().isEmpty())
        {
            Toast.makeText(view_permanente.getContext(), "Ingrese su fecha de nacimiento.", Toast.LENGTH_LONG).show();
            alertDialog.dismiss();
            return;
        }
        fecha_nacimientoEditText.setText(fecha_nacimientoEditText.getText().toString().trim());
        if(!new Calculo().validarFecha(fecha_nacimientoEditText.getText().toString()))
        {
            Toast.makeText(view_permanente.getContext(), "Fecha de nacimiento ingresada no valida.", Toast.LENGTH_LONG).show();
            alertDialog.dismiss();
            return;
        }
        if(nombreCuentaEditText.getText().toString().isEmpty())
        {
            Toast.makeText(view_permanente.getContext(), "Ingrese el nombre de cuenta para iniciar sesion", Toast.LENGTH_LONG).show();
            alertDialog.dismiss();
            return;
        }
        if(existe_nombre_cuenta)
        {
            Toast.makeText(view_permanente.getContext(), "Este nombre de cuenta esta siendo utilizado por otro usuario", Toast.LENGTH_LONG).show();
            alertDialog.dismiss();
            return;
        }
        if(contraseñaCuentaEditText.getText().toString().isEmpty())
        {
            Toast.makeText(view_permanente.getContext(), "Ingrese la contraseña de la cuenta", Toast.LENGTH_LONG).show();
            alertDialog.dismiss();
            return;
        }
        if(verificarContraseñaCuentaEditText.getText().toString().isEmpty())
        {
            Toast.makeText(view_permanente.getContext(), "Por favor ingrese la contraseña a verificar", Toast.LENGTH_LONG).show();
            alertDialog.dismiss();
            return;
        }
        if(!contraseñaCuentaEditText.getText().toString().equals(verificarContraseñaCuentaEditText.getText().toString()))
        {
            Toast.makeText(view_permanente.getContext(), "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
            alertDialog.dismiss();
            return;
        }
        final Usuario usuario = new Usuario();
        usuario.nombres_usuario = nombreUsuarioEditText.getText().toString();
        usuario.apellidos_usuario = apellidoEditText.getText().toString();
        usuario.fecha_nacimiento = new Calculo().fechaNormal(fecha_nacimientoEditText.getText().toString());
        if(masculinoRadioButton.isChecked())
        {
            usuario.sexo_usuario = 0;
        }
        else
        {
            usuario.sexo_usuario = 1;
        }
        if(imagen_eliminada)
        {
            usuario.foto_perfil_usuario = "-1";
        }
        else
        {
            usuario.foto_perfil_usuario = bitmap_conver_to_String(bitmap);
        }
        usuario.nombre_cuenta_usuario = nombreCuentaEditText.getText().toString().toLowerCase();
        usuario.contrasena_usuario = contraseñaCuentaEditText.getText().toString();
        HashMap<String, String> hashMap = new Gestion_usuario().registrar_usuario(usuario);
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try
                {
                    ArrayList<Usuario> usuarios = new Gestion_usuario().generar_json(response);
                    if(!usuarios.isEmpty())
                    {
                        Gestion_usuario.setUsuario_online(usuarios.get(0));
                        Gestion_usuario.getUsuario_online().contrasena_usuario = contraseñaCuentaEditText.getText().toString();
                        Toast.makeText(view_permanente.getContext(),"Usuario registrado con exito, ha iniciado sesion.", Toast.LENGTH_LONG).show();
                        SharedPreferences prefs = getActivity().getSharedPreferences("SESION_USER", Context.MODE_PRIVATE);
                        SharedPreferences.Editor myEditor = prefs.edit();
                        myEditor.putString("USER", Gestion_usuario.getUsuario_online().nombre_cuenta_usuario);
                        myEditor.putString("PASS", Gestion_usuario.getUsuario_online().contrasena_usuario);
                        limpiar();
                        myEditor.commit();
                        getActivity().finish();
                    }
                    else
                    {
                        Toast.makeText(view_permanente.getContext(),"Usuario no registrado, intente nuevamente.", Toast.LENGTH_LONG).show();
                    }
                }
                catch (NumberFormatException exc)
                {
                    Toast.makeText(view_permanente.getContext(),"Usuario no registrado", Toast.LENGTH_LONG).show();
                    alertDialog.dismiss();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(view_permanente.getContext(),"Error en el servidor", Toast.LENGTH_LONG).show();
                alertDialog.dismiss();
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(view_permanente.getContext()).addToRequestQueue(stringRequest);
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    private void limpiar()
    {
        nombreUsuarioEditText.setText("");
        apellidoEditText.setText("");
        fecha_nacimientoEditText.setText("");
        fecha_nacimientoEditText.setTextColor(getResources().getColor(R.color.Black));
        nombreCuentaEditText.setText("");
        contraseñaCuentaEditText.setText("");
        verificarContraseñaCuentaEditText.setText("");
        imagen_eliminada = false;
        imagen_modificada = false;
        bitmap = null;
        fotoPerfilCircleImageView.setImageBitmap(null);
    }

    public void tomarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE)
        {
            imageUri = data.getData();
            fotoPerfilCircleImageView.setImageURI(imageUri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(view_permanente.getContext().getContentResolver(), imageUri);
                imagen_modificada = true;
                imagen_eliminada = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            fotoPerfilCircleImageView.setImageBitmap(bitmap);
            imagen_modificada = true;
            imagen_eliminada = false;
        }
    }

    private String bitmap_conver_to_String(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream);
        byte[] bytes = stream.toByteArray();
        String s = Base64.encodeToString(bytes, Base64.DEFAULT);
        return s;
    }

    private void showDatePickerDialog()
    {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = year + "-" + (month+1)  + "-" + day ;
                fecha_nacimientoEditText.setText(selectedDate);
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
