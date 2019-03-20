package com.comfacesar.comfacesar.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.comfacesar.comfacesar.Dialog.DatePickerFragment;
import com.comfacesar.comfacesar.R;
import com.example.extra.Config;
import com.example.extra.MySocialMediaSingleton;
import com.example.extra.WebService;
import com.example.gestion.Gestion_administrador;
import com.example.gestion.Gestion_movil_registro;
import com.example.gestion.Gestion_usuario;
import com.example.modelo.Administrador;
import com.example.modelo.Movil_registro;
import com.example.modelo.Usuario;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class ModificarUsuarioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ModificarUsuarioFragment() {
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
    public static ModificarUsuarioFragment newInstance(String param1, String param2) {
        ModificarUsuarioFragment fragment = new ModificarUsuarioFragment();
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
    private EditText numeroIdentificacionEditText;
    private EditText nombreUsuarioEditText;
    private EditText apellidoEditText;
    private EditText nombreCuentaEditText;
    private EditText contraseñaCuentaEditText;
    private RadioButton masculinoRadioButton;
    private RadioButton femeninoRadioButton;
    private EditText telefonoEditText;
    private EditText direccionEditText;
    private Button modificar_usuario;
    private EditText fecha_nacimientoEditText;
    private EditText correo_electronicoEditText;
    private Usuario usuario_espejo;
    private ImageView fotoPerfilImageView;
    private Button subirFotoButton;
    private Button tomarFotoButton;
    private Button eliminarFotoButton;
    private boolean imagen_eliminada;
    private Bitmap bitmap;
    private static final int PICK_IMAGE = 100;
    private int REQUEST_IMAGE_CAPTURE = 1;
    private Uri imageUri;
    private boolean imagen_modificada;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view_permanente = inflater.inflate(R.layout.fragment_modificar_datos_personales_usuario, container, false);
        numeroIdentificacionEditText = view_permanente.findViewById(R.id.numeroIdentificacionUsuarioEditText);
        nombreUsuarioEditText = view_permanente.findViewById(R.id.nombresUsuarioEditText);
        apellidoEditText = view_permanente.findViewById(R.id.apellidosUsuarioEditText);
        masculinoRadioButton = view_permanente.findViewById(R.id.masculinoUsuarioRadioButton);
        femeninoRadioButton = view_permanente.findViewById(R.id.femeninoUsuarioRadioButton);
        modificar_usuario = view_permanente.findViewById(R.id.modificarUsuarioButton);
        direccionEditText = view_permanente.findViewById(R.id.direccionUsuarioEditText);
        telefonoEditText = view_permanente.findViewById(R.id.telefonoUsuarioEditText);
        fecha_nacimientoEditText = view_permanente.findViewById(R.id.edadUsuarioEditText);
        correo_electronicoEditText = view_permanente.findViewById(R.id.correoEletronicoUsuarioEditText);
        fotoPerfilImageView = view_permanente.findViewById(R.id.fotoPerfilImageView);
        actualizar_perfil();
        subirFotoButton = view_permanente.findViewById(R.id.subirFotoButton);
        tomarFotoButton = view_permanente.findViewById(R.id.tomarFotoButton);
        eliminarFotoButton = view_permanente.findViewById(R.id.eliminar_imagenButton);
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
                fotoPerfilImageView.setImageBitmap(null);
            }
        });
        fecha_nacimientoEditText.setFocusable(false);
        usuario_espejo = Gestion_usuario.getUsuario_online();
        evento_fecha_nacimiento();
        evento_modificar_usuario();
        cargar_datos_usuario();
        return view_permanente;
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
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
            fotoPerfilImageView.setImageURI(imageUri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(view_permanente.getContext().getContentResolver(), imageUri);
                imagen_modificada = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            fotoPerfilImageView.setImageBitmap(bitmap);
            imagen_modificada = true;
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

    private void evento_fecha_nacimiento()
    {
        fecha_nacimientoEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        fecha_nacimientoEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    showDatePickerDialog();
                }
            }
        });
    }

    private void evento_modificar_usuario()
    {
        modificar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(Config.getImei() == null)
            {
                Toast.makeText(view_permanente.getContext(), "Acepte los permiso primero antes de modificar los datos personales de su cuenta.", Toast.LENGTH_LONG).show();
                return;
            }
            if(nombreUsuarioEditText.getText().toString().isEmpty())
            {
                Toast.makeText(view_permanente.getContext(), "Ingrese su nombres", Toast.LENGTH_LONG).show();
                return;
            }
            if(apellidoEditText.getText().toString().isEmpty())
            {
                Toast.makeText(view_permanente.getContext(), "Ingrese sus apellidos", Toast.LENGTH_LONG).show();
                return;
            }
            if(masculinoRadioButton.isChecked())
            {
                usuario_espejo.sexo_usuario = 0;
            }
            else
            {
                usuario_espejo.sexo_usuario = 1;
            }
            usuario_espejo.foto_perfil_usuario = Gestion_usuario.getUsuario_online().foto_perfil_usuario;
            usuario_espejo.foto_perfil_anterior = Gestion_usuario.getUsuario_online().foto_perfil_usuario;
            if(imagen_modificada)
            {
                if(bitmap != null)
                {
                    usuario_espejo.foto_perfil_usuario = bitmap_conver_to_String(bitmap);
                }
            }
            usuario_espejo.numero_identificacion_usuario = numeroIdentificacionEditText.getText().toString();
            usuario_espejo.nombres_usuario = nombreUsuarioEditText.getText().toString();
            usuario_espejo.apellidos_usuario = apellidoEditText.getText().toString();
            usuario_espejo.fecha_nacimiento = fecha_nacimientoEditText.getText().toString();
            usuario_espejo.telefono_usuario = telefonoEditText.getText().toString();
            usuario_espejo.direccion_usuario = direccionEditText.getText().toString();
            usuario_espejo.correo_usuario = correo_electronicoEditText.getText().toString();
            HashMap<String, String> hashMap = new Gestion_usuario().modificar_datos_personales(usuario_espejo);
            Response.Listener<String> stringListener = new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    int val = 0;
                    try
                    {
                        val = Integer.parseInt(response);
                        if(val > 0)
                        {
                            actualizar_perfil();
                            Gestion_usuario.getUsuario_online().nombres_usuario = usuario_espejo.nombres_usuario;
                            Gestion_usuario.getUsuario_online().apellidos_usuario = usuario_espejo.apellidos_usuario;
                            Gestion_usuario.getUsuario_online().direccion_usuario = usuario_espejo.direccion_usuario;
                            Gestion_usuario.getUsuario_online().telefono_usuario = usuario_espejo.telefono_usuario;
                            Gestion_usuario.getUsuario_online().correo_usuario = usuario_espejo.correo_usuario;
                            Gestion_usuario.getUsuario_online().sexo_usuario = usuario_espejo.sexo_usuario;
                            Gestion_usuario.getUsuario_online().fecha_nacimiento = usuario_espejo.fecha_nacimiento;
                            Toast.makeText(view_permanente.getContext(),"Datos personales actualizados", Toast.LENGTH_LONG).show();
                        }
                    }
                    catch (NumberFormatException exc)
                    {
                        Toast.makeText(view_permanente.getContext(),"Error al actualizar datos personales", Toast.LENGTH_LONG).show();
                    }
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(view_permanente.getContext(),"Ha ocurrido un error en el servidor", Toast.LENGTH_LONG).show();
                }
            };
            StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),hashMap,stringListener, errorListener);
            MySocialMediaSingleton.getInstance(view_permanente.getContext()).addToRequestQueue(stringRequest);
            }
        });
    }

    private void actualizar_perfil()
    {
        HashMap<String,String> params = new Gestion_usuario().consultar_usuario_por_id(Gestion_usuario.getUsuario_online());
        Log.d("parametros", params.toString());
        Response.Listener<String> stringListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                //aqui llega la respuesta, dependiendo del tipo de la consulta la proceso
                Log.d("response", response);
                if(!response.equals(""))
                {
                    ArrayList<Usuario> usuarios = new Gestion_usuario().generar_json(response);
                    if(!usuarios.isEmpty())
                    {
                        Usuario usuario = usuarios.get(0);
                        Gestion_usuario.getUsuario_online().foto_perfil_usuario = usuario.foto_perfil_usuario;
                        Picasso.with(getContext()).load(usuario.foto_perfil_usuario).into(fotoPerfilImageView);
                    }
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("Response.error", error.toString());
            }
        };
        StringRequest stringRequest = MySocialMediaSingleton.volley_consulta(WebService.getUrl(),params,stringListener, errorListener);
        MySocialMediaSingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private void cargar_datos_usuario()
    {
        numeroIdentificacionEditText.setText(usuario_espejo.numero_identificacion_usuario);
        nombreUsuarioEditText.setText(usuario_espejo.nombres_usuario);
        apellidoEditText.setText(usuario_espejo.apellidos_usuario);
        fecha_nacimientoEditText.setText(usuario_espejo.fecha_nacimiento);
        Picasso.with(getContext()).load(usuario_espejo.foto_perfil_usuario).into(fotoPerfilImageView);
        if(usuario_espejo.sexo_usuario == 0)
        {
            masculinoRadioButton.setChecked(true);
        }
        else
        {
            femeninoRadioButton.setChecked(true);
        }
        telefonoEditText.setText(usuario_espejo.telefono_usuario);
        direccionEditText.setText(usuario_espejo.direccion_usuario);
        correo_electronicoEditText.setText(usuario_espejo.correo_usuario);
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
