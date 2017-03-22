package refactoring_guru.patterns.memento.shape_editor.editor;

import refactoring_guru.patterns.memento.shape_editor.commands.Command;
import refactoring_guru.patterns.memento.shape_editor.history.History;
import refactoring_guru.patterns.memento.shape_editor.history.Memento;
import refactoring_guru.patterns.memento.shape_editor.shapes.CompoundShape;
import refactoring_guru.patterns.memento.shape_editor.shapes.Shape;

import javax.swing.*;
import java.io.*;
import java.util.Base64;

public class Editor extends JComponent {
    private EditorCanvas canvas;
    private CompoundShape allShapes = new CompoundShape();
    private History history;

    public Editor() {
        canvas = new EditorCanvas(this);
        history = new History();
    }

    public void loadShapes(Shape... shapes) {
        allShapes.clear();
        allShapes.add(shapes);
        canvas.refresh();
    }

    public CompoundShape getShapes() {
        return allShapes;
    }

    public void execute(Command c) {
        history.push(c, new Memento(this));
        c.execute();
    }

    public void undo() {
        history.undo();
        canvas.repaint();
    }

    public void redo() {
        history.redo();
        canvas.repaint();
    }

    public String backup() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this.allShapes);
            oos.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) {
            return "";
        }
    }

    public void restore(String state) {
        try {
            byte[] data = Base64.getDecoder().decode(state);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            this.allShapes = (CompoundShape) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException e) {
            System.out.print("ClassNotFoundException occurred.");
        } catch (IOException e) {
            System.out.print("IOException occurred.");
        }
    }
}
